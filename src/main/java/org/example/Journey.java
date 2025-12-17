package org.example;

import jakarta.persistence.*;

@Entity
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "traveler_id", nullable = false)
    private Traveler traveler;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "from_location_id", nullable = false)
    private Location fromLocation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "to_location_id", nullable = false)
    private Location toLocation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;

    @Column(name = "turn_number", nullable = false)
    private int turnNumber;

    public Journey() {}

    public Journey(Traveler traveler, Location fromLocation, Location toLocation, Transport transport) {
        this.traveler = traveler;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.transport = transport;
        this.turnNumber = 0;
    }

}
