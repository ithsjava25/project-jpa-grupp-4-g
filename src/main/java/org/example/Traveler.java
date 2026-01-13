package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Traveler extends playerMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_location_id")
    private Location targetLocation;

    @Column(name = "remainingDistance", nullable = false)
    private int remainingDistance;

    @Column(name = "turn_count", nullable = false)
    private int turnCount = 0;

    @Column(nullable = false)
    private BigDecimal money = BigDecimal.valueOf(credits);

    @Column
    private int score = 0;

    protected Traveler() {}

    public Traveler(String name, Location startLocation) {
        playerName = name;
        this.currentLocation = startLocation;

        // håller gui-position i sync med location
        setPosition();
        // destinationPos kan vara null tills spelaren väljer, så vi sätter ingen destination här
    }

    public Long getId() {
        return id;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public int getRemainingDistance() {
        return remainingDistance;
    }

    public boolean isTravelling() {
        return targetLocation != null;
    }

    /**
     * håller playerPosX/Y i sync med currentLocation (för gui)
     */
    public void setPosition() {
        if (currentLocation != null) {
            setPlayerPosX(currentLocation.getX());
            setPlayerPosY(currentLocation.getY());
        }
    }

    /**
     * (behåll) används för "fri" gui-rörelse när destination kommer från klick på karta
     * OBS: den här räknar manhattan via destinationPosX/Y
     */
    public void startJourney(Location target) {
        this.targetLocation = target;

        // ✅ viktigt: synca destinationPos så att calculateDistance funkar
        setDestinationPos(target.getX(), target.getY());

        calculateDistanceFromDestinationPos();
    }

    /**
     * ✅ NY: används av JourneyService när du reser via LocationLink.distance
     * då ska remainingDistance baseras på routeDistance (inte koordinater)
     */
    public void startJourney(Location target, int routeDistance) {
        if (routeDistance < 0) throw new IllegalArgumentException("routeDistance must be >= 0");

        this.targetLocation = target;
        this.remainingDistance = routeDistance;

        // ändå bra för gui att visa destination
        setDestinationPos(target.getX(), target.getY());
    }

    /**
     * gamla logiken, men tydligare namn
     */
    private void calculateDistanceFromDestinationPos() {
        // använder destinationPos som sätts via setDestinationPos(...)
        int xPos = Math.abs(currentLocation.getX() - getDestinationPosX());
        int yPos = Math.abs(currentLocation.getY() - getDestinationPosY());
        this.remainingDistance = xPos + yPos;
    }

    /**
     * när vi avancerar på en länk-baserad resa använder vi remainingDistance direkt
     */
    public void advance(int distanceThisTurn) {
        remainingDistance -= distanceThisTurn;
        turnCount++;

        if (remainingDistance <= 0) {
            currentLocation = targetLocation;
            targetLocation = null;
            remainingDistance = 0;

            // ✅ synca gui-position när du "kommer fram"
            setPosition();
        }
    }

    public void pay(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("amount cannot be null");
        if (amount.signum() < 0) throw new IllegalArgumentException("amount must be >= 0");

        if (money.compareTo(amount) < 0) {
            throw new IllegalStateException("not enough money");
        }

        money = money.subtract(amount);

        // ✅ håll gamla credits i sync så gui/cli fortfarande visar rätt
        this.credits = money.intValue();
    }

    public void addMoney(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("amount cannot be null");
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }

        money = money.add(amount);

        // ✅ sync credits
        this.credits = money.intValue();
    }

    /**
     * om du fortfarande använder gamla "updateJourney()" i någon kod:
     * den bör i princip inte användas i nya link-baserade flödet.
     * men om du vill behålla den utan att den sabbar:
     */
    public void updateJourney() {
        turnCount = getTurns();
        setPosition();

        // ⚠️ bara meningsfullt om destinationPos är satt (fri gui-rörelse)
        if (isTravelling()) {
            calculateDistanceFromDestinationPos();
        }
    }
    public void subtractMoneyClamped(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        BigDecimal newValue = this.money.subtract(amount);
        if (newValue.signum() < 0) {
            this.money = BigDecimal.ZERO;
        } else {
            this.money = newValue;
        }
    }

    public void deductMoney(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        BigDecimal next = this.money.subtract(amount);
        this.money = next.signum() < 0 ? BigDecimal.ZERO : next;
    }

}
