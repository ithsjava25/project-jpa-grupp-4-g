package org.example;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"from_location_id", "to_location_id"}))
public class LocationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_location_id")
    private Location fromLocation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_location_id")
    private Location toLocation;

    private int distance;
    public LocationLink(Location fromLocation, Location toLocation, int distance) {}




}
