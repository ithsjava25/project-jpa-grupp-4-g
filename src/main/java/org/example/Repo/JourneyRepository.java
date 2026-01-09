package org.example.Repo;

import jakarta.persistence.EntityManager;
import org.example.Journey;

public class JourneyRepository {

    private final EntityManager em;

    public JourneyRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Journey journey) {
        em.persist(journey);
    }
}

