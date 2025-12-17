package org.example;

import jakarta.persistence.*;

@Entity
public class Traveler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;

    @Column(name = "turn_count", nullable = false)
    private int turnCount = 0;

    protected Traveler() {}

    public Traveler(String name, Location startLocation) {
        this.name = name;
        this.currentLocation = startLocation;
    }

    public Long getId() {
        return id;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }
}
