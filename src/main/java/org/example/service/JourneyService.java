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

    public Journey performTurn(Traveler traveler, PossibleMoves move) {

        LocationLink route = move.getRoute();
        Transport transport = move.getTransport();

        boolean allowed = route.getTransportLinks()
            .stream()
            .anyMatch(tl -> tl.getTransport().equals(transport));

        if (!allowed) {
            throw new IllegalStateException(transport.getType() + " is not allowed on this route");
        }

        BigDecimal cost = transport.getCostPerMove();
        if (traveler.getMoney().compareTo(cost) < 0) {
            throw new IllegalStateException("traveler cannot afford this move");
        }

        if (!traveler.isTravelling()) {
            traveler.startJourney(route.getToLocation(), route.getDistance());
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

        // ✅ events: skapa + persistera kopplat till journey
        var events = eventService.applyEndOfTurnEvents(traveler);
        for (var e : events) {
            em.persist(new TurnEvent(
                traveler,
                journey,
                e.type(),
                e.amount(),
                e.message()
            ));
        }

        em.merge(traveler);

        return journey;
    }

}
