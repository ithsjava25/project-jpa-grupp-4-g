package org.example;

import jakarta.persistence.*;
import org.example.service.BootstrapService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        EntityManager em = emf.createEntityManager();
        new BootstrapService(em).initialize();
        EntityTransaction tx = em.getTransaction();
        boolean wonGame = false;
        Traveler p1;
        Traveler p2;
        Traveler p3;
        Traveler p4;

        List<Transport> transportMethods = em.createQuery("select t from Transport t", Transport.class).getResultList();


        try {
            tx.begin();
            int pAmount = 0;
            String input = IO.readln("Welcome, how many players??? 2 - 4  ");
            try {
                pAmount = Integer.parseInt(input);
            } catch (NumberFormatException e){
                System.out.println("Not a number");
            }

            switch (pAmount){
//                Test case for debuging and testing
//                case 1 ->{
//                    while(true){
//                    Location current = randomLocation(em);
//                    Location dest = randomLocation(em);
//
//                    System.out.println(current.getName());
//                    System.out.println(dest.getName());}
//                }
                case 2 ->{
                    String p1Name = IO.readln("Input name for player 1: ");
                    Location newDestP1 = randomLocation(em);
                    p1 = new Traveler(p1Name, randomLocation(em), newDestP1);
                    p1.setDestinationPos(newDestP1.getX(), newDestP1.getY());
                    String p2Name = IO.readln("Input name for player 2: ");
                    Location newDestP2 = randomLocation(em);
                    p2 = new Traveler(p2Name, randomLocation(em), newDestP2);
                    p2.setDestinationPos(newDestP2.getX(), newDestP2.getY());
                    em.persist(p1);
                    em.persist(p2);
                    while(!wonGame){
                        p1.playerTurn(transportMethods);
                        p2.playerTurn(transportMethods);
                        if (p1.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p1.setDestinationPos(newDest.getX(), newDest.getY());
                        }else if(p2.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p2.setDestinationPos(newDest.getX(), newDest.getY());
                        }
                        if(p1.checkScore()){
                            System.out.println(p1.playerName + " wins");
                            wonGame = true;
                        } else if(p2.checkScore()){
                            System.out.println(p2.playerName + " wins");
                            wonGame = true;
                        }
                        p1.updateJourney();
                        p2.updateJourney();
                        em.persist(p1);
                        em.persist(p2);
                    }

                }case 3 ->{
                    String p1Name = IO.readln("Input name for player 1: ");
                    Location newDestP1 = randomLocation(em);
                    p1 = new Traveler(p1Name, randomLocation(em), newDestP1);
                    p1.setDestinationPos(newDestP1.getX(), newDestP1.getY());
                    String p2Name = IO.readln("Input name for player 2: ");
                    Location newDestP2 = randomLocation(em);
                    p2 = new Traveler(p2Name, randomLocation(em), newDestP2);
                    p2.setDestinationPos(newDestP2.getX(), newDestP2.getY());
                    String p3Name = IO.readln("Input name for player 2: ");
                    Location newDestP3 = randomLocation(em);
                    p3 = new Traveler(p3Name, randomLocation(em), newDestP3);
                    p3.setDestinationPos(newDestP3.getX(), newDestP3.getY());
                    em.persist(p1);
                    em.persist(p2);
                    em.persist(p3);

                    while(!wonGame){
                        p1.playerTurn(transportMethods);
                        p2.playerTurn(transportMethods);
                        p3.playerTurn(transportMethods);
                        if (p1.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p1.setDestinationPos(newDest.getX(), newDest.getY());
                        }else if(p2.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p2.setDestinationPos(newDest.getX(), newDest.getY());
                        }else if(p3.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p3.setDestinationPos(newDest.getX(), newDest.getY());
                        }
                        if(p1.checkScore()){
                            System.out.println(p1.playerName + " wins");
                            wonGame = true;
                        } else if(p2.checkScore()){
                            System.out.println(p2.playerName + " wins");
                            wonGame = true;
                        } else if(p3.checkScore()){
                            System.out.println(p3.playerName + " wins");
                            wonGame = true;
                        }
                        p1.updateJourney();
                        p2.updateJourney();
                        p3.updateJourney();
                        em.persist(p1);
                        em.persist(p2);
                        em.persist(p3);
                    }

                }case 4 ->{
                    String p1Name = IO.readln("Input name for player 1: ");
                    Location newDestP1 = randomLocation(em);
                    p1 = new Traveler(p1Name, randomLocation(em), newDestP1);
                    p1.setDestinationPos(newDestP1.getX(), newDestP1.getY());
                    String p2Name = IO.readln("Input name for player 2: ");
                    Location newDestP2 = randomLocation(em);
                    p2 = new Traveler(p2Name, randomLocation(em), newDestP2);
                    p2.setDestinationPos(newDestP2.getX(), newDestP2.getY());
                    String p3Name = IO.readln("Input name for player 3: ");
                    Location newDestP3 = randomLocation(em);
                    p3 = new Traveler(p3Name, randomLocation(em), newDestP3);
                    p3.setDestinationPos(newDestP3.getX(), newDestP3.getY());
                    String p4Name = IO.readln("Input name for player 4: ");
                    Location newDestP4 = randomLocation(em);
                    p4 = new Traveler(p4Name, randomLocation(em), newDestP4);
                    p4.setDestinationPos(newDestP4.getX(), newDestP4.getY());
                    em.persist(p1);
                    em.persist(p2);
                    em.persist(p3);
                    em.persist(p4);
                    while(!wonGame){
                        p1.playerTurn(transportMethods);
                        p2.playerTurn(transportMethods);
                        p3.playerTurn(transportMethods);
                        p4.playerTurn(transportMethods);
                        if (p1.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p1.setDestinationPos(newDest.getX(), newDest.getY());
                        }else if(p2.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p2.setDestinationPos(newDest.getX(), newDest.getY());
                        }else if(p3.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p3.setDestinationPos(newDest.getX(), newDest.getY());
                        }else if(p4.checkIfPlayerIsAtDestination()){
                            Location newDest = randomLocation(em);
                            p4.setDestinationPos(newDest.getX(), newDest.getY());
                        }
                        if(p1.checkScore()){
                            System.out.println(p1.playerName + " wins");
                            wonGame = true;
                        } else if(p2.checkScore()){
                            System.out.println(p2.playerName + " wins");
                            wonGame = true;
                        } else if(p3.checkScore()){
                            System.out.println(p3.playerName + " wins");
                            wonGame = true;
                        }else if(p4.checkScore()){
                            System.out.println(p3.playerName + " wins");
                            wonGame = true;
                        }
                        p1.updateJourney();
                        p2.updateJourney();
                        p3.updateJourney();
                        p4.updateJourney();
                        em.persist(p1);
                        em.persist(p2);
                        em.persist(p3);
                        em.persist(p4);
                    }
                }
            }

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
//
//
//            bob.playerTurn(airplane.getDiceCount());
//            int rolled = bob.getAvailableMovement();
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

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
//    random locations
//    public static Location randLoc(EntityManager em){
//        Long locSize = em.createQuery("select count(l) from Location l", Long.class).getSingleResult();
//        Location randLoc = em.createQuery("select l from Location l", Location.class).setFirstResult(randomIndex(locSize)).setMaxResults(1).getSingleResult();
//        return randLoc;
//    }

    //Randomise through all points for continent, country and locations.
    static public Location randomLocation(EntityManager em){

        Long contCount = em.createQuery("Select count(c) from Continent c", Long.class).getSingleResult();

        Continent randCont = em.createQuery("select c from Continent c", Continent.class).setFirstResult(randomIndex(contCount)).setMaxResults(1).getSingleResult();

        while(randCont.getId() == 2){
            randCont = em.createQuery("select c from Continent c", Continent.class).setFirstResult(randomIndex(contCount)).setMaxResults(1).getSingleResult();
        }

        Long countryCount = em.createQuery("Select count(c) from Country c where c.continent = :continent", Long.class)
            .setParameter("continent", randCont)
            .getSingleResult();

        Country randCountry = em.createQuery("select c from Country c where continent = :continent", Country.class)
            .setParameter("continent", randCont)
            .setFirstResult(randomIndex(countryCount))
            .setMaxResults(1).getSingleResult();

        Long locCount = em.createQuery("select count(o) from Location o where o.country = :country", Long.class)
            .setParameter("country", randCountry)
            .getSingleResult();

       while(locCount == 0){
            randCountry = em.createQuery("select c from Country c where continent = :continent", Country.class)
                .setParameter("continent", randCont)
                .setFirstResult(randomIndex(countryCount))
                .setMaxResults(1).getSingleResult();

            locCount = em.createQuery("select count(o) from Location o where o.country = :country", Long.class)
                .setParameter("country", randCountry)
                .getSingleResult();
        }

        Location randLocation = em.createQuery("select l from Location l where l.country = :country", Location.class)
            .setParameter("country", randCountry).setFirstResult(randomIndex(locCount)).setMaxResults(1).getSingleResult();

        System.out.println(randLocation.getName() + " " + randLocation.getX() + " " + randLocation.getY());

        return randLocation;
    }

    static public int randomIndex(long indexes){
        if (indexes <= 0) {
            throw new IllegalArgumentException("indexes must be > 0");
        }
        return (int) (Math.random() * indexes);
    }
}

