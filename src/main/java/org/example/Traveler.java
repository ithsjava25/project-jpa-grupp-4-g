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
    private BigDecimal money = BigDecimal.valueOf(credits);

    protected Traveler() {}

    public Traveler(String name, Location startLocation,Location destination) {
        playerName = name;
        this.currentLocation = startLocation;
        setPosition();
        startJourney(destination);
    }

    public void setPosition(){
        setPlayerPosX(currentLocation.getX());
        setPlayerPosY(currentLocation.getY());
    }

    public void updateJourney(){
        turnCount = getTurns();
        setPosition();
        calculateDistance();
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


    public void startJourney(Location target) {
        this.targetLocation = target;
        calculateDistance();
    }

    private void calculateDistance() {
        int xPos = 0;
        int yPos = 0;
        if (currentLocation.getX() > getDestinationPosX()){
            xPos = currentLocation.getX() - getDestinationPosX();
        } else {
            xPos = getDestinationPosX() - currentLocation.getX();
        }
        if (currentLocation.getY() > getDestinationPosY()){
            yPos = currentLocation.getY() - getDestinationPosY();
        } else {
            yPos = getDestinationPosY() - currentLocation.getY();
        }
        this.remainingDistance = xPos + yPos;
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
