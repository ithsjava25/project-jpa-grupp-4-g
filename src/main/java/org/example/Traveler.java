package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Traveler", indexes = {
    @Index(name = "idx_traveler_score", columnList = "score")
})
public class Traveler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name", nullable = false, length = 60)
    private String playerName;

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

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal money = BigDecimal.valueOf(10000);

    @Column(nullable = false)
    private int score = 0;

    // ✅ gui-position i db (så du slipper legacy-klasser)
    @Column(name = "pos_x", nullable = false)
    private int posX;

    @Column(name = "pos_y", nullable = false)
    private int posY;

    protected Traveler() {}

    public Traveler(String name, Location startLocation) {
        setPlayerName(name);
        this.currentLocation = startLocation;
        syncPositionFromCurrentLocation();
    }

    // getters/setters
    public Long getId() { return id; }
    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) {
        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException("playerName cannot be blank");
        }
        this.playerName = playerName;
    }

    public Location getCurrentLocation() { return currentLocation; }
    public Location getTargetLocation() { return targetLocation; }
    public int getRemainingDistance() { return remainingDistance; }
    public int getTurnCount() { return turnCount; }
    public BigDecimal getMoney() { return money; }
    public int getScore() { return score; }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }

    public boolean isTravelling() { return targetLocation != null; }

    public void addScore(int delta) {
        if (delta < 0) throw new IllegalArgumentException("delta must be >= 0");
        this.score += delta;
    }

    public void pay(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("amount cannot be null");
        if (amount.signum() < 0) throw new IllegalArgumentException("amount must be >= 0");
        if (money.compareTo(amount) < 0) throw new IllegalStateException("not enough money");
        money = money.subtract(amount);
    }

    public void addMoney(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("amount cannot be null");
        if (amount.signum() < 0) throw new IllegalArgumentException("amount must be positive");
        money = money.add(amount);
    }

    public void deductMoney(BigDecimal amount) {
        if (amount.signum() < 0) throw new IllegalArgumentException("amount must be positive");
        BigDecimal next = this.money.subtract(amount);
        this.money = next.signum() < 0 ? BigDecimal.ZERO : next;
    }

    public void syncPositionFromCurrentLocation() {
        if (currentLocation != null) {
            this.posX = currentLocation.getX();
            this.posY = currentLocation.getY();
        }
    }

    public void startJourney(Location target, int routeDistance) {
        if (routeDistance < 0) throw new IllegalArgumentException("routeDistance must be >= 0");
        this.targetLocation = target;
        this.remainingDistance = routeDistance;
    }

    public boolean advance(int distanceThisTurn) {
        remainingDistance -= distanceThisTurn;
        turnCount++;

        if (remainingDistance <= 0) {
            currentLocation = targetLocation;
            targetLocation = null;
            remainingDistance = 0;

            syncPositionFromCurrentLocation();
            return true; // ✅ arrived this turn
        }
        return false;
    }

    public void cancelJourney() {
        this.targetLocation = null;
        this.remainingDistance = 0;
    }
}

