package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Traveler extends playerMovement{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_location_id")
    private Location targetLocation;

    private int remainingDistance;

    @Column(name = "turn_count", nullable = false)
    private int turnCount = 0;

    @Column(nullable = false)
    private BigDecimal money;

    protected Traveler() {}

    public Traveler(String name, Location startLocation, String money) {
        playerName = name;
        this.currentLocation = startLocation;
        this.money = new BigDecimal(money);
    }

    public Long getId() {
        return id;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public boolean isTravelling() {
        return targetLocation != null;
    }


    public void startJourney(Location target, int distance) {
        this.targetLocation = target;
        this.remainingDistance = distance;
    }

    public void advance(int distanceThisTurn) {
        remainingDistance -= distanceThisTurn;
        turnCount++;

        if (remainingDistance <= 0) {
            currentLocation = targetLocation;
            targetLocation = null;
            remainingDistance = 0;
        }
    }

    public void pay(BigDecimal amount) {
        if (money.compareTo(amount) < 0) {
            throw new IllegalStateException("not enough money");
        }
        money = money.subtract(amount);
    }

    public int getRemainingDistance() {
        return remainingDistance;
    }
}
