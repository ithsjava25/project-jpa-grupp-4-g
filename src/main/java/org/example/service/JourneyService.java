package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourneyService {

    private final EntityManager em;
    private final PlayerEventService eventService;

    public JourneyService(EntityManager em, PlayerEventService eventService) {
        this.em = em;
        this.eventService = eventService;
    }

    public List<PossibleMoves> findPossibleMoves(Location fromLocation) {
        List<LocationLink> routes = em.createQuery("""
            select distinct ll
            from LocationLink ll
            join fetch ll.transportLinks tl
            join fetch tl.transport
            where ll.fromLocation = :location
        """, LocationLink.class)
            .setParameter("location", fromLocation)
            .getResultList();

        List<PossibleMoves> result = new ArrayList<>();
        for (LocationLink route : routes) {
            for (TransportLink tl : route.getTransportLinks()) {
                result.add(new PossibleMoves(route, tl.getTransport()));
            }
        }
        return result;
    }

    public Journey startNewJourneyTurn(Traveler traveler, PossibleMoves move) {
        if (traveler.isTravelling()) {
            throw new IllegalStateException("already travelling – cannot start a new journey");
        }

        LocationLink route = move.getRoute();
        Transport transport = move.getTransport();

        boolean allowed = route.getTransportLinks().stream()
            .anyMatch(tl -> tl.getTransport().equals(transport));
        if (!allowed) {
            throw new IllegalStateException(transport.getType() + " is not allowed on this route");
        }

        traveler.startJourney(route.getToLocation(), route.getDistance());

        return doTravelStep(traveler, route, transport);
    }

    public Journey continueCurrentJourneyTurn(Traveler traveler) {
        if (!traveler.isTravelling()) {
            throw new IllegalStateException("not travelling – choose a move first");
        }

        Journey last = em.createQuery("""
            select j
            from Journey j
            where j.traveler = :t
            order by j.turnNumber desc
        """, Journey.class)
            .setParameter("t", traveler)
            .setMaxResults(1)
            .getSingleResult();

        LocationLink route = last.getLocationLink();
        Transport transport = last.getTransport();

        return doTravelStep(traveler, route, transport);
    }

    private Journey doTravelStep(Traveler traveler, LocationLink route, Transport transport) {

        BigDecimal cost = transport.getCostPerMove();
        if (traveler.getMoney().compareTo(cost) < 0) {
            throw new IllegalStateException("traveler cannot afford this move");
        }

        int rolledDistance = transport.rollDistance();

        traveler.pay(cost);

        boolean arrived = traveler.advance(rolledDistance); // gör advance return boolean
        if (arrived) {
            traveler.addScore(1);
        }


        Journey journey = new Journey(
            traveler,
            route,
            transport,
            rolledDistance,
            traveler.getRemainingDistance(),
            traveler.getTurnCount()
        );

        em.persist(journey);
        em.merge(traveler);

        // hämta events och persista TurnEvent kopplat till denna Journey
        List<PlayerEventService.EventResult> events =
            eventService.applyEndOfTurnEvents(traveler);

        for (PlayerEventService.EventResult er : events) {
            TurnEvent te = new TurnEvent(
                traveler,
                journey,
                er.type(),
                er.amount(),
                er.message()
            );
            em.persist(te);
        }

        // (valfritt) om du vill vara extra säker att de skrivs direkt i samma tx:
        em.flush();

        return journey;
    }

    public Map<Long, Integer> getLastTurnNumbersForPlayers(List<Long> travelerIds) {
        if (travelerIds == null || travelerIds.isEmpty()) return Map.of();

        List<Object[]> rows = em.createQuery("""
        select j.traveler.id, max(j.turnNumber)
        from Journey j
        where j.traveler.id in :ids
        group by j.traveler.id
    """, Object[].class)
            .setParameter("ids", travelerIds)
            .getResultList();

        Map<Long, Integer> out = new HashMap<>();
        for (Object[] r : rows) {
            Long id = (Long) r[0];
            Integer maxTurn = (r[1] == null) ? 0 : ((Number) r[1]).intValue();
            out.put(id, maxTurn);
        }

        // spelare utan journeys hamnar inte i rows → sätt 0 på dem
        for (Long id : travelerIds) {
            out.putIfAbsent(id, 0);
        }

        return out;
    }

}
