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

    /**
     * listar alla möjliga drag från en given plats
     * (en rutt + ett transportsätt = ett PossibleMoves)
     */
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
     * utför ett drag (en tur)
     */
    public Journey performTurn(Traveler traveler, PossibleMoves move) {

        LocationLink route = move.getRoute();
        Transport transport = move.getTransport();

        // 1) kontroll: är transport tillåten på rutten?
        boolean allowed = route.getTransportLinks()
            .stream()
            .anyMatch(tl -> tl.getTransport().equals(transport));

        if (!allowed) {
            throw new IllegalStateException(
                transport.getType() + " is not allowed on this route"
            );
        }

        // 2) kontroll: har resenären råd?
        BigDecimal cost = transport.getCostPerMove();
        if (traveler.getMoney().compareTo(cost) < 0) {
            throw new IllegalStateException("traveler cannot afford this move");
        }

        // 3) starta resa om det är en ny resa
        if (!traveler.isTravelling()) {
            traveler.startJourney(route.getToLocation(), route.getDistance());
        }

        // 4) slå tärning
        int rolledDistance = transport.rollDistance();

        // 5) betala
        traveler.pay(cost);

        // 6) flytta
        traveler.advance(rolledDistance);

        // 7) logga draget
        Journey journey = new Journey(
            traveler,
            route,
            transport,
            rolledDistance,
            traveler.getRemainingDistance(),
            traveler.getTurnCount()
        );

        em.persist(journey);
        // traveler är oftast managed här, men merge är ok om du ibland skickar in detached
        em.merge(traveler);

        // 8) event (EN gång, här)
        eventService.applyEndOfTurnEvents(traveler);

        return journey;
    }
}
