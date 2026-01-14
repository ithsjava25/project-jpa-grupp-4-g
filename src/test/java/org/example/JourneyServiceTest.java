package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.service.JourneyService;
import org.example.service.PlayerEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Lyckades inte testa med riktiga JPA-entiteter för den senaste versionen av LocationLink,
 * så mockar används istället. På det sättet finns åtminstone tester som verifierar den centrala logiken.
 */
public class JourneyServiceTest {

    private EntityManager em;
    private PlayerEventService eventService;
    private JourneyService service;

    /**
     * Initialiserar testmiljön inför varje test.
     * Skapar en mockad EntityManager för att simulera databasoperationer,
     * och en mockad PlayerEventService för att hantera spelhändelser.
     * Dessa används sedan för att skapa en instans av JourneyService.
     */
    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class);
        eventService = mock(PlayerEventService.class);
        service = new JourneyService(em, eventService);
    }

    /**
     * Testar att en resenär kan starta en resa korrekt med en tillåten transport.
     * Verifierar att resenären markeras som "på väg" och att Journey-objektet kopplas korrekt till resenären, rutten och transporten.
     * Mockade LocationLink och TransportLink används för att isolera testet från JPA.
     */
    @Test
    void startJourney() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 5, 5);
        Traveler traveler = new Traveler("Bob", from);
        traveler.addMoney(BigDecimal.valueOf(1000));

        Transport transport = mock(Transport.class);
        when(transport.getCostPerMove()).thenReturn(BigDecimal.valueOf(10));
        when(transport.rollDistance()).thenReturn(1);
        when(transport.getType()).thenReturn("BUSS");

        LocationLink route = mock(LocationLink.class);
        when(route.getFromLocation()).thenReturn(from);
        when(route.getToLocation()).thenReturn(to);
        when(route.getDistance()).thenReturn(6);

        TransportLink tl = mock(TransportLink.class);
        when(tl.getTransport()).thenReturn(transport);
        Set<TransportLink> links = new HashSet<>();
        links.add(tl);
        when(route.getTransportLinks()).thenReturn(links);

        PossibleMoves move = new PossibleMoves(route, transport);

        when(eventService.applyEndOfTurnEvents(traveler)).thenReturn(List.of());

        Journey journey = service.startNewJourneyTurn(traveler, move);

        assertTrue(traveler.isTravelling());
        assertEquals(traveler, journey.getTraveler());
        assertEquals(route, journey.getLocationLink());
        assertEquals(transport, journey.getTransport());
    }

    /**
     * Testar att en resenär inte kan starta en ny resa när en pågående resa redan finns.
     * Verifierar att startNewJourneyTurn kastar IllegalStateException med korrekt felmeddelande.
     * Säkerställer att reselogiken inte tillåter parallella resor för samma resenär.
     * Mockade LocationLink och TransportLink används för att isolera testet från JPA.
     */
    @Test
    void startJourneyWhileTravelling() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 5, 5);
        Traveler traveler = new Traveler("Bob", from);
        traveler.startJourney(to, 6);

        Transport transport = mock(Transport.class);
        when(transport.getType()).thenReturn("BUSS");

        LocationLink route = mock(LocationLink.class);
        when(route.getFromLocation()).thenReturn(from);
        when(route.getToLocation()).thenReturn(to);
        when(route.getDistance()).thenReturn(6);

        TransportLink tl = mock(TransportLink.class);
        when(tl.getTransport()).thenReturn(transport);
        Set<TransportLink> links = new HashSet<>();
        links.add(tl);
        when(route.getTransportLinks()).thenReturn(links);

        PossibleMoves move = new PossibleMoves(route, transport);

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> service.startNewJourneyTurn(traveler, move)
        );

        assertEquals("already travelling – cannot start a new journey", ex.getMessage());
    }

    /**
     * Testar att det inte går att använda en transport som inte är tillåten på rutten.
     * Ska kasta IllegalStateException med korrekt felmeddelande.
     * Mockade LocationLink och TransportLink används för att isolera testet från JPA.
     */
    @Test
    void disallowedTransport() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 5, 5);
        Traveler traveler = new Traveler("Bob", from);
        traveler.addMoney(BigDecimal.valueOf(1000));

        Transport allowed = mock(Transport.class);
        when(allowed.getCostPerMove()).thenReturn(BigDecimal.valueOf(10));
        when(allowed.rollDistance()).thenReturn(1);
        when(allowed.getType()).thenReturn("BUSS");

        Transport disallowed = mock(Transport.class);
        when(disallowed.getCostPerMove()).thenReturn(BigDecimal.valueOf(15));
        when(disallowed.rollDistance()).thenReturn(1);
        when(disallowed.getType()).thenReturn("TRAIN");

        LocationLink route = mock(LocationLink.class);
        when(route.getFromLocation()).thenReturn(from);
        when(route.getToLocation()).thenReturn(to);
        when(route.getDistance()).thenReturn(6);

        TransportLink tl = mock(TransportLink.class);
        when(tl.getTransport()).thenReturn(allowed);
        Set<TransportLink> links = new HashSet<>();
        links.add(tl);
        when(route.getTransportLinks()).thenReturn(links);

        PossibleMoves move = new PossibleMoves(route, disallowed);

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> service.startNewJourneyTurn(traveler, move)
        );

        assertEquals(disallowed.getType() + " is not allowed on this route", ex.getMessage());
    }

    /**
     * Testar att en resenär inte kan starta en resa om saldot är mindre än transportkostnaden.
     * Ska kasta IllegalStateException med meddelandet "traveler cannot afford this move".
     * Mockade LocationLink och TransportLink används för att isolera testet från JPA.
     */
    @Test
    void notEnoughMoney() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 5, 5);
        Traveler traveler = new Traveler("Bob", from);

        traveler.subtractMoneyClamped(traveler.getMoney());
        traveler.addMoney(BigDecimal.valueOf(5));

        Transport expensiveTransport = mock(Transport.class);
        when(expensiveTransport.getCostPerMove()).thenReturn(BigDecimal.valueOf(10));
        when(expensiveTransport.rollDistance()).thenReturn(1);
        when(expensiveTransport.getType()).thenReturn("BUSS");

        LocationLink route = mock(LocationLink.class);
        when(route.getFromLocation()).thenReturn(from);
        when(route.getToLocation()).thenReturn(to);
        when(route.getDistance()).thenReturn(6);

        TransportLink tl = mock(TransportLink.class);
        when(tl.getTransport()).thenReturn(expensiveTransport);
        Set<TransportLink> links = new HashSet<>();
        links.add(tl);
        when(route.getTransportLinks()).thenReturn(links);

        PossibleMoves move = new PossibleMoves(route, expensiveTransport);

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> service.startNewJourneyTurn(traveler, move)
        );

        assertEquals("traveler cannot afford this move", ex.getMessage());
    }

    /**
     * Testar att en pågående resa fortsätter korrekt.
     * Verifierar att Journey kopplas till rätt Traveler, Transport och LocationLink,
     * samt att resenären fortfarande är markerad som "på väg" och återstående distans uppdateras.
     * Mockade LocationLink, TransportLink, EntityManager och PlayerEventService används för att isolera testet från JPA.
     */
    @Test
    void continueJourney() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 5, 5);

        Traveler traveler = new Traveler("Bob", from);
        traveler.addMoney(BigDecimal.valueOf(1000));
        traveler.startJourney(to, 6);

        Transport transport = mock(Transport.class);
        when(transport.getCostPerMove()).thenReturn(BigDecimal.valueOf(10));
        when(transport.rollDistance()).thenReturn(1);
        when(transport.getType()).thenReturn("BUSS");

        LocationLink route = mock(LocationLink.class);
        when(route.getFromLocation()).thenReturn(from);
        when(route.getToLocation()).thenReturn(to);
        when(route.getDistance()).thenReturn(6);

        TransportLink tl = mock(TransportLink.class);
        when(tl.getTransport()).thenReturn(transport);
        Set<TransportLink> links = new HashSet<>();
        links.add(tl);
        when(route.getTransportLinks()).thenReturn(links);

        Journey lastJourney = new Journey(traveler, route, transport, 0, 6, 0);
        TypedQuery<Journey> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Journey.class))).thenReturn(queryMock);
        when(queryMock.setParameter("t", traveler)).thenReturn(queryMock);
        when(queryMock.setMaxResults(1)).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(lastJourney);

        when(eventService.applyEndOfTurnEvents(traveler)).thenReturn(List.of());

        Journey journey = service.continueCurrentJourneyTurn(traveler);

        assertEquals(traveler, journey.getTraveler());
        assertEquals(route, journey.getLocationLink());
        assertEquals(transport, journey.getTransport());
        assertTrue(traveler.isTravelling());
        assertTrue(traveler.getRemainingDistance() >= 0);
    }

    /**
     * Testar att findPossibleMoves returnerar rätt antal moves för en route med flera transporter.
     * Verifierar att varje PossibleMoves är kopplad till rätt route och transport.
     * Mockade LocationLink, TransportLink och EntityManager används för att isolera testet från JPA.
     */
    @Test
    void findMoves() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 1, 1);

        Transport bus = mock(Transport.class);
        when(bus.getType()).thenReturn("BUSS");
        when(bus.getCostPerMove()).thenReturn(BigDecimal.valueOf(10));

        Transport train = mock(Transport.class);
        when(train.getType()).thenReturn("TRAIN");
        when(train.getCostPerMove()).thenReturn(BigDecimal.valueOf(20));

        LocationLink route = mock(LocationLink.class);
        when(route.getFromLocation()).thenReturn(from);
        when(route.getToLocation()).thenReturn(to);
        when(route.getDistance()).thenReturn(5);

        TransportLink tlBus = mock(TransportLink.class);
        when(tlBus.getTransport()).thenReturn(bus);
        TransportLink tlTrain = mock(TransportLink.class);
        when(tlTrain.getTransport()).thenReturn(train);

        Set<TransportLink> links = new HashSet<>();
        links.add(tlBus);
        links.add(tlTrain);
        when(route.getTransportLinks()).thenReturn(links);

        TypedQuery<LocationLink> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(LocationLink.class))).thenReturn(queryMock);
        when(queryMock.setParameter("location", from)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(route));

        List<PossibleMoves> moves = service.findPossibleMoves(from);

        assertEquals(2, moves.size(), "Det ska finnas två möjliga moves");

        for (PossibleMoves move : moves) {
            assertTrue(move.getTransport().equals(bus) || move.getTransport().equals(train));
            assertEquals(route, move.getRoute());
        }
    }
}
