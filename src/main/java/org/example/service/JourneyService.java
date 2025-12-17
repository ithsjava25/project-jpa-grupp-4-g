package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.Journey;
import org.example.Location;
import org.example.Repo.JourneyRepository;
import org.example.Transport;
import org.example.Traveler;

public class JourneyService {

    private final EntityManager em;
    private final JourneyRepository journeyRepository;

    public JourneyService(EntityManager em) {
        this.em = em;
        this.journeyRepository = new JourneyRepository(em);
    }

    public Journey performTurn(
        Traveler traveler,
        Location toLocation,
        Transport transport
    ) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // säkerställ att player är managed
            Traveler managedTraveler = em.find(Traveler.class, traveler.getId());

            Location fromLocation = managedTraveler.getCurrentLocation();
            int nextTurn = managedTraveler.getTurnCount() + 1;

            // validering
            if (fromLocation.equals(toLocation)) {
                throw new IllegalArgumentException("cannot travel to same location");
            }

            Journey journey = new Journey(
                managedTraveler,
                fromLocation,
                toLocation,
                transport,
                nextTurn
            );

            journeyRepository.save(journey);

            // uppdatera player state
            managedPlayer.setCurrentLocation(toLocation);
            managedPlayer.setTurnCount(nextTurn);

            tx.commit();
            return journey;

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}

