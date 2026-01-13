package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.*;

import java.util.List;

public class JourneyService {

    private final EntityManager em;

    public JourneyService(EntityManager em) {
        this.em = em;
    }

    public List<PossibleMoves> findPossibleMoves(Location currentLocation) {

        List<Object[]> rows = em.createQuery("""
        select ll, t
        from LocationLink ll
        join ll.transportLinks tl
        join tl.transport t
        where ll.fromLocation = :location
    """, Object[].class)
            .setParameter("location", currentLocation)
            .getResultList();

        return rows.stream()
            .map(row -> new PossibleMoves(
                (LocationLink) row[0],
                (Transport) row[1]
            ))
            .toList();
    }


    public Journey playTurn(
        Long travelerId,
        Location targetLocation,
        Transport transport
    ) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Traveler traveler = em.find(Traveler.class, travelerId);
            if (traveler == null) {
                throw new IllegalArgumentException("traveler not found");
            }

            Location from = traveler.getCurrentLocation();



            // hämta rutt
            LocationLink locationLink = em.createQuery("""
            select ll
            from LocationLink ll
            where ll.fromLocation = :from
              and ll.toLocation = :to
        """, LocationLink.class)
                .setParameter("from", from)
                .setParameter("to", targetLocation)
                .getResultStream()
                .findFirst()
                .orElseThrow(() ->
                    new IllegalArgumentException("no valid route between locations")
                );

            // slå tärningar
            int rolled = transport.rollDistance();

            int before = traveler.getRemainingDistance();
            traveler.advance(rolled);
            int after = traveler.getRemainingDistance();

            int moved = before - after;

            int turnNumber = traveler.getTurnCount() + 1;
            traveler.setTurnCount(turnNumber);

            Journey journey = new Journey(
                traveler,
                locationLink,
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

}


