package org.example;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class TravelerTest {

    private Location dummyLocation(int x, int y) {
        return new Location("Test", LocationType.CITY, null, x, y);
    }

    /**
     * Testar att konstruktorn för Traveler sätter spelarens startposition korrekt.
     * Skapar en resenär med en viss startposition och kontrollerar att
     * playerPosX och playerPosY matchar koordinaterna för startpositionen.
     */
    @Test
    void constructorPosition() {
        Location start = dummyLocation(3, 7);
        Traveler traveler = new Traveler("Bob", start);

        assertEquals(3, traveler.getPlayerPosX());
        assertEquals(7, traveler.getPlayerPosY());
    }

    /**
     * Testar att en ny resenär inte är på väg när den skapas.
     * Skapar en Traveler på en startposition och kontrollerar att
     * isTravelling() returnerar false innan någon resa har startats.
     */
    @Test
    void notTravellingInitially() {
        Traveler traveler = new Traveler("Bob", dummyLocation(0, 0));
        assertFalse(traveler.isTravelling());
    }

    /**
     * Testar att resenären börjar en resa korrekt.
     * Kontrollerar att målet och återstående avstånd sätts,
     * samt att resenären markeras som "på väg".
     */
    @Test
    void startJourneySetsTarget() {
        Location start = dummyLocation(0, 0);
        Location target = dummyLocation(10, 10);

        Traveler traveler = new Traveler("Bob", start);
        traveler.startJourney(target, 15);

        assertTrue(traveler.isTravelling());
        assertEquals(15, traveler.getRemainingDistance());
    }

    /**
     * Testar att resenären förflyttar sig korrekt under en tur.
     * Kontrollerar att återstående avstånd minskar med rätt antal steg
     * och att tur-räknaren ökar med 1 när resan fortsätter.
     */
    @Test
    void advanceReducesDistance() {
        Traveler traveler = new Traveler("Bob", dummyLocation(0, 0));
        traveler.startJourney(dummyLocation(5, 5), 10);

        traveler.advance(3);

        assertEquals(7, traveler.getRemainingDistance());
        assertEquals(1, traveler.getTurnCount());
        assertTrue(traveler.isTravelling());
    }

    /**
     * Testar att resan slutförs när resenären förflyttas längre än återstående avstånd.
     * Kontrollerar att återstående avstånd blir 0, att resenären inte längre är "på väg",
     * och att resenären hamnar på målet.
     */
    @Test
    void advanceCompletesJourney() {
        Location target = dummyLocation(5, 5);
        Traveler traveler = new Traveler("Bob", dummyLocation(0, 0));

        traveler.startJourney(target, 5);
        traveler.advance(10);

        assertEquals(0, traveler.getRemainingDistance());
        assertFalse(traveler.isTravelling());
        assertEquals(target, traveler.getCurrentLocation());
    }

    /**
     * Testar att betalning minskar resenärens pengar korrekt.
     * Skapar en resenär med ett visst saldo och drar av en summa.
     * Kontrollerar att spelarens pengar minskar med rätt belopp.
     */
    @Test
    void payReducesMoney() {
        Traveler traveler = new Traveler("Bob", dummyLocation(0, 0));
        BigDecimal startMoney = traveler.getMoney();

        BigDecimal amount = startMoney.divide(BigDecimal.valueOf(2));
        traveler.pay(amount);

        assertEquals(startMoney.subtract(amount), traveler.getMoney());
    }

    /**
     * Testar att betalning kastar ett fel om resenären inte har tillräckligt med pengar.
     * Skapar en resenär och försöker ta ut mer pengar än vad som finns.
     * Kontrollerar att ett IllegalStateException kastas med rätt felmeddelande.
     */
    @Test
    void payNotEnoughMoneyThrows() {
        Traveler traveler = new Traveler("Bob", dummyLocation(0, 0));
        BigDecimal cost = traveler.getMoney().add(BigDecimal.ONE);

        Exception exception = assertThrows(IllegalStateException.class, () -> traveler.pay(cost));
        assertEquals("not enough money", exception.getMessage());
    }
}
