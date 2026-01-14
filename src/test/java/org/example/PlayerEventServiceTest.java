package org.example;

import org.example.service.PlayerEventService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerEventServiceTest {

    private Traveler dummyTraveler() {
        Traveler t = new Traveler("Bob", new Location("Stockholm", LocationType.CAPITAL, null, 0, 0));
        t.addMoney(BigDecimal.valueOf(1000));
        return t;
    }

    /**
     * Verifierar att metoden applyEndOfTurnEvents kan köras utan undantag.
     * Testet säkerställer att en lista alltid returneras, även om inga events
     * genereras, och att metoden inte kraschar när en Traveler skickas in.
     */
    @Test
    void runWithoutError() {
        Traveler traveler = dummyTraveler();
        PlayerEventService service = new PlayerEventService();

        List<PlayerEventService.EventResult> events = service.applyEndOfTurnEvents(traveler);

        assertNotNull(events);
    }

    /**
     * Verifierar att applyEndOfTurnEvents aldrig returnerar fler än två events.
     * Testet säkerställer att listan kan innehålla 0, 1 eller 2 EventResult-objekt
     * beroende på slumpen, men aldrig fler.
     */
    @Test
    void eventCountWithinBounds() {
        Traveler traveler = dummyTraveler();
        PlayerEventService service = new PlayerEventService();

        List<PlayerEventService.EventResult> events = service.applyEndOfTurnEvents(traveler);

        assertTrue(events.size() >= 0 && events.size() <= 2);
    }

    /**
     * Säkerställer att resenärens saldo aldrig blir negativt efter
     * att applyEndOfTurnEvents har körts. Testet verifierar att
     * event som drar pengar inte kan göra money < 0.
     */
    @Test
    void moneyNotNegative() {
        Traveler traveler = dummyTraveler();
        PlayerEventService service = new PlayerEventService();

        service.applyEndOfTurnEvents(traveler);

        assertTrue(traveler.getMoney().compareTo(BigDecimal.ZERO) >= 0);
    }

    /**
     * Verifierar att alla genererade events har korrekt EventType
     * (BONUS eller PENALTY) och att beloppet alltid är positivt.
     * Säkerställer att applyEndOfTurnEvents aldrig skapar ogiltiga events.
     */
    @Test
    void eventsHaveValidTypeAndAmount() {
        Traveler traveler = dummyTraveler();
        PlayerEventService service = new PlayerEventService();

        List<PlayerEventService.EventResult> events = service.applyEndOfTurnEvents(traveler);

        for (PlayerEventService.EventResult e : events) {
            assertTrue(e.type() == EventType.BONUS || e.type() == EventType.PENALTY);
            assertTrue(e.amount().compareTo(BigDecimal.ZERO) > 0);
        }
    }

    /**
     * Säkerställer att alla EventResult som genereras av
     * applyEndOfTurnEvents innehåller ett meddelande.
     * Testet verifierar att message aldrig är null, även om inga events skapas.
     */
    @Test
    void eventsHaveMessage() {
        Traveler traveler = dummyTraveler();
        PlayerEventService service = new PlayerEventService();

        List<PlayerEventService.EventResult> events = service.applyEndOfTurnEvents(traveler);

        for (PlayerEventService.EventResult e : events) {
            assertNotNull(e.message());
        }
    }
}
