package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * startar en ny resa på en vald route+transport (bara om du INTE redan reser)
     */
    public Journey startNewJourneyTurn(Traveler traveler, PossibleMoves move) {
        if (traveler.isTravelling()) {
            throw new IllegalStateException("already travelling – cannot start a new journey");
        }

        LocationLink route = move.getRoute();
        Transport transport = move.getTransport();

        // kontroll: transport tillåten?
        boolean allowed = route.getTransportLinks().stream()
            .anyMatch(tl -> tl.getTransport().equals(transport));
        if (!allowed) {
            throw new IllegalStateException(transport.getType() + " is not allowed on this route");
        }

        // starta resa med routeDistance
        traveler.startJourney(route.getToLocation(), route.getDistance());

        // genomför första “del-turen” på resan
        return doTravelStep(traveler, route, transport);
    }

    /**
     * fortsätter pågående resa (samma route+transport som senaste journey)
     */
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

    /**
     * gemensam logik för ett “steg” på en resa (slå, betala, advance, logga, events)
     */
    private Journey doTravelStep(Traveler traveler, LocationLink route, Transport transport) {

        BigDecimal cost = transport.getCostPerMove();
        if (traveler.getMoney().compareTo(cost) < 0) {
            throw new IllegalStateException("traveler cannot afford this move");
        }

        int rolledDistance = transport.rollDistance();

        traveler.pay(cost);
        traveler.advance(rolledDistance);

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

        // events kopplas till denna turn (om ni persisterar TurnEvent senare görs det här)
        eventService.applyEndOfTurnEvents(traveler);

        return journey;
    }
}
