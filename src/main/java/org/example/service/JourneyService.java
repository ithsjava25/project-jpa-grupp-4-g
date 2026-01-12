package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.*;

public class JourneyService {

    private final EntityManager em;

    public JourneyService(EntityManager em) {
        this.em = em;
    }

    /**
     * Spelar en hel tur:
     * - validerar transport
     * - drar pengar
     * - slår tärningar
     * - flyttar traveler
     * - sparar Journey
     */
    public Journey playTurn(
        Long travelerId,
        Long locationLinkId,
        Long transportId
    ) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Traveler traveler = em.find(Traveler.class, travelerId);
            LocationLink route = em.find(LocationLink.class, locationLinkId);
            Transport transport = em.find(Transport.class, transportId);

            if (traveler == null || route == null || transport == null) {
                throw new IllegalArgumentException("entity not found");
            }

            // 1. validera att transport är tillåten
            validateTransportAllowed(route, transport);

            // 2. starta resa om behövs
            if (!traveler.isTravelling()) {
                traveler.startJourney(
                    route.getToLocation(),
                    route.getDistance()
                );
            }

            // 3. betala
            traveler.pay(transport.getCostPerMove());

            // 4. slå tärningar
            int rolled = transport.rollDistance();

            int before = traveler.getRemainingDistance();
            traveler.advance(rolled);
            int after = traveler.getRemainingDistance();

            int moved = before - after;
            int turnNumber = traveler.getTurnCount();

            // 5. spara journey (ALLTID, även om ej framme)
            Journey journey = new Journey(
                traveler,
                route,
                transport,
                moved,
                after,
                turnNumber
            );

            em.persist(journey);

            tx.commit();
            return journey;

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    private void validateTransportAllowed(
        LocationLink route,
        Transport transport
    ) {
        boolean allowed = em.createQuery(
                """
                select count(tl)
                from TransportLink tl
                where tl.locationLink = :route
                  and tl.transport = :transport
                """,
                Long.class
            )
            .setParameter("route", route)
            .setParameter("transport", transport)
            .getSingleResult() > 0;

        if (!allowed) {
            throw new IllegalStateException(
                transport.getType()
                    + " not allowed on this route"
            );
        }
    }
}

