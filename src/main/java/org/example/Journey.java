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
     * vilken rutt som används from + to + distance
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
     * hur långt spelaren förflyttade sig denna tur
     */
    @Column(name = "distance_moved", nullable = false)
    private int distanceMoved;

    /**
     * hur långt som återstår på rutten efter draget
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
        int distanceMoved,
        int remainingDistance,
        int turnNumber
    ) {
        this.traveler = traveler;
        this.locationLink = locationLink;
        this.transport = transport;
        this.distanceMoved = distanceMoved;
        this.remainingDistance = remainingDistance;
        this.turnNumber = turnNumber;
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

    public int getDistanceMoved() {
        return distanceMoved;
    }

    public int getRemainingDistance() {
        return remainingDistance;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

}
