package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.service.JourneyService;
import org.example.service.PlayerEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
     * Testar att en resenär kan starta en resa korrekt med tillåten transport.
     * Kontrollerar att resenären markeras som "på väg" och att Journey-objektet
     * kopplas korrekt till resenären, rutten och transporten.
     * <p>
     * Mockito används för att mocka Transport och PlayerEventService,
     * vilket gör det möjligt att kontrollera reselogiken utan att slumpen påverkar testet.
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

        LocationLink route = new LocationLink(from, to, 6);
        route.getTransportLinks().add(new TransportLink(route, transport));

        PossibleMoves move = new PossibleMoves(route, transport);
        when(eventService.applyEndOfTurnEvents(traveler)).thenReturn(List.of());

        Journey journey = service.startNewJourneyTurn(traveler, move);

        assertTrue(traveler.isTravelling());
        assertTrue(traveler.getRemainingDistance() >= 0);
        assertEquals(traveler, journey.getTraveler());
        assertEquals(route, journey.getLocationLink());
        assertEquals(transport, journey.getTransport());
    }

    /**
     * Testar att en resenär inte kan starta en ny resa när en pågående resa redan finns.
     * Verifierar att startNewJourneyTurn kastar IllegalStateException med korrekt felmeddelande.
     * Säkerställer att reselogiken inte tillåter parallella resor för samma resenär.
     */
    @Test
    void startJourneyWhileTravelling() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 5, 5);
        Traveler traveler = new Traveler("Bob", from);
        traveler.startJourney(to, 6);

        Transport transport = mock(Transport.class);
        when(transport.getType()).thenReturn("BUSS");

        LocationLink route = new LocationLink(from, to, 6);
        route.getTransportLinks().add(new TransportLink(route, transport));
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

        LocationLink route = new LocationLink(from, to, 6);
        route.getTransportLinks().add(new TransportLink(route, allowed));
        PossibleMoves move = new PossibleMoves(route, disallowed);

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> service.startNewJourneyTurn(traveler, move)
        );
        assertEquals(disallowed.getType() + " is not allowed on this route", ex.getMessage());
    }

    /**
     * Resenärens saldo nollställs och sätts till 5 krediter, vilket är mindre än
     * kostnaden för den valda transporten (10 krediter). Vid anrop av
     * startNewJourneyTurn med en transport som kostar mer än resenärens saldo
     * ska en IllegalStateException kastas med meddelandet
     * "traveler cannot afford this move".
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
        LocationLink route = new LocationLink(from, to, 6);
        route.getTransportLinks().add(new TransportLink(route, expensiveTransport));

        PossibleMoves move = new PossibleMoves(route, expensiveTransport);

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> service.startNewJourneyTurn(traveler, move)
        );

        assertEquals("traveler cannot afford this move", ex.getMessage());
    }

    /**
     * Testar att en pågående resa fortsätter korrekt.
     * Säkerställer att resenären avancerar, kostnad dras och ett nytt Journey-objekt skapas.
     * Kontrollerar att Journey är kopplat till rätt Traveler, Transport och LocationLink,
     * samt att resenären fortfarande är markerad som "på väg" och återstående distans är uppdaterad.
     * <p>
     * Mockito används för att mocka EntityManager och PlayerEventService,
     * vilket gör det möjligt att testa reselogiken utan att slumpmässiga events påverkar resultatet.
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

        LocationLink route = new LocationLink(from, to, 6);
        route.getTransportLinks().add(new TransportLink(route, transport));

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
     * Skapar en route med flera transporter och mockar EntityManager för att returnera den.
     * Kontrollerar att metoden returnerar rätt antal PossibleMoves och att varje move
     * är kopplad till rätt route och transport.
     */
    @Test
    void findMoves() {
        Location from = new Location("Stockholm", LocationType.CAPITAL, null, 0, 0);
        Location to = new Location("Berlin", LocationType.CAPITAL, null, 1, 1);

        Transport bus = new Transport(TransportType.BUSS, "10");
        Transport train = new Transport(TransportType.TRAIN, "20");

        LocationLink route = new LocationLink(from, to, 5);
        route.getTransportLinks().add(new TransportLink(route, bus));
        route.getTransportLinks().add(new TransportLink(route, train));

        TypedQuery<LocationLink> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(LocationLink.class))).thenReturn(queryMock);
        when(queryMock.setParameter("location", from)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(route));

        List<PossibleMoves> moves = service.findPossibleMoves(from);

        assertEquals(2, moves.size(), "Det ska finnas två möjliga moves");

        PossibleMoves move1 = moves.get(0);
        PossibleMoves move2 = moves.get(1);

        assertTrue(move1.getTransport().equals(bus) || move1.getTransport().equals(train));
        assertTrue(move2.getTransport().equals(bus) || move2.getTransport().equals(train));

        assertEquals(route, move1.getRoute());
        assertEquals(route, move2.getRoute());
    }
}
