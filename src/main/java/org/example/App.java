package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.service.BootstrapService;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        EntityManager em = emf.createEntityManager();
        new BootstrapService(em).initialize();

        //EntityTransaction tx = em.getTransaction();

//        try {
//            tx.begin();
//            Continent europe = new Continent("Europe");
//            em.persist(europe);
//
//            Country sweden = new Country("Sweden", europe);
//            em.persist(sweden);
//
//            Country germany = new Country("Germany", europe);
//            em.persist(germany);
//
//            Country france = new Country("France", europe);
//            em.persist(france);
//
//            Location stockholm = new Location("Stockholm", LocationType.CAPITAL, sweden, 1, 1);
//            em.persist(stockholm);
//
//            Location berlin = new Location("Berlin", LocationType.CAPITAL, germany, 1, 2);
//            em.persist(berlin);
//
//            Location paris = new Location("Paris", LocationType.CAPITAL, france, 2, 3);
//            em.persist(paris);
//
//            LocationLink locationLink = new LocationLink(stockholm,berlin,10);
//            em.persist(locationLink);
//
//            Transport airplane = new Transport(TransportType.AIRPLANE,"1000");
//            em.persist(airplane);
//
//            Transport train = new Transport(TransportType.TRAIN,"500");
//            em.persist(train);
//
//            Transport bus = new Transport(TransportType.BUSS,"250");
//            em.persist(bus);
//
//            TransportLink transportLink = new TransportLink(locationLink,airplane);
//            em.persist(transportLink);
//
//            Traveler bob = new Traveler("Bob", stockholm,"10000");
//            em.persist(bob);
//
//            /**
//             * testtur
//             */
//            LocationLink route = locationLink;
//            Transport plane = airplane;
//            System.out.println("Bob starts in " + bob.getCurrentLocation().getName());
//
//            //startar resan
//            bob.startJourney(berlin, route.getDistance());
//
//            System.out.println("Bob has money before move: " + bob.getMoney());
//
//            bob.pay(plane.getCostPerMove());
//
//            System.out.println(
//                "Bob pays " + plane.getCostPerMove() +
//                    " for AIRPLANE. Money left: " + bob.getMoney()
//            );
//
//
//            // slå tärningar
//            int rolled = plane.rollDistance();
//            System.out.println("Bob rolls " + rolled + " using AIRPLANE (3d6)");
//
//            // flytta
//            bob.advance(rolled);
//
//            Journey journey = new Journey(
//                bob,
//                route,
//                plane,
//                rolled,
//                bob.getRemainingDistance(),
//                bob.getTurnCount()
//            );
//
//            em.persist(journey);
//
//            // resultat
//            if (!bob.isTravelling()) {
//                System.out.println("Bob arrived in Berlin in ONE turn!");
//            } else {
//                System.out.println(
//                    "Bob did NOT arrive. Remaining distance: "
//                        + bob.getRemainingDistance()
//                );
//            }
//
//            tx.commit();
//
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            throw e;
//        } finally {
//            em.close();
//        }
    }
}

