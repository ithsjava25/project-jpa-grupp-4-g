package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.service.JourneyService;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        EntityManager em = emf.createEntityManager();

        /**
         * Testtur som använder JourneyService med playTurn-metodanrop
         */

        try {
            JourneyService journeyService = new JourneyService(em);

            Traveler bob = em.find(Traveler.class, 1L);
            LocationLink route = em.find(LocationLink.class, 1L);
            Transport plane = em.find(Transport.class, 1L);

            System.out.println("Bob starts in " + bob.getCurrentLocation().getName());
            System.out.println("Bob has money before move: " + bob.getMoney());

            Journey journey = journeyService.playTurn(
                    bob.getId(),
                    route.getId(),
                    plane.getID()
                );

            if(journey.getRemainingDistance() == 0) {
                System.out.println("Bob arrived in one turn!");
            }else{
                System.out.println("Bob moved "  + journey.getDistanceMoved() + journey.getRemainingDistance() + " moves remaining.");
            }

        } finally {
            em.close();
        }
    }
}

