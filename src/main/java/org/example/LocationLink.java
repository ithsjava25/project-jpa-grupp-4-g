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

    @Column(nullable = false)
    private int distance;

    public LocationLink() {}

    public LocationLink(Location fromLocation, Location toLocation, int distance) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }
    public Location getFromLocation() {
        return fromLocation;
    }
    public Location getToLocation() {
        return toLocation;
    }
    public int getDistance() {
        return distance;
    }
}
