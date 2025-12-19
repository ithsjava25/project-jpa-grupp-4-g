package org.example;

import jakarta.persistence.*;

@Entity
@Table(
    name = "journey",
    indexes = {
        @Index(name = "idx_journey_traveler", columnList = "traveler_id"),
        @Index(name = "idx_journey_turn", columnList = "turn_number")
    }
)
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * vem gör draget
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "traveler_id", nullable = false)
    private Traveler traveler;

    /**
     * vilken godkänd rutt som används
     * (from + to + distance definieras av LocationLink)
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_link_id", nullable = false)
    private LocationLink locationLink;

    /**
     * vilket transportmedel som används denna tur
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;

    /**
     * hur långt man slog denna tur (tärningar)
     */
    @Column(name = "distance_rolled", nullable = false)
    private int distanceRolled;

    /**
     * hur långt som återstår på rutten efter detta drag
     */
    @Column(name = "remaining_distance", nullable = false)
    private int remainingDistance;

    /**
     * vilken tur i ordningen detta är för spelaren
     */
    @Column(name = "turn_number", nullable = false)
    private int turnNumber;

    /* -------- constructors -------- */

    protected Journey() {
        // for jpa
    }

    public Journey(
        Traveler traveler,
        LocationLink locationLink,
        Transport transport,
        int distanceRolled,
        int remainingDistance,
        int turnNumber
    ) {
        this.traveler = traveler;
        this.locationLink = locationLink;
        this.transport = transport;
        this.distanceRolled = distanceRolled;
        this.remainingDistance = remainingDistance;
        this.turnNumber = turnNumber;
    }

    public Journey(Traveler managedTraveler, Location fromLocation, Location toLocation, Transport transport, int nextTurn) {
    }

    /* -------- getters -------- */

    public Long getId() {
        return id;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public LocationLink getLocationLink() {
        return locationLink;
    }

    public Transport getTransport() {
        return transport;
    }

    public int getDistanceRolled() {
        return distanceRolled;
    }

    public int getRemainingDistance() {
        return remainingDistance;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
