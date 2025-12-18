package org.example;

import jakarta.persistence.*;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private LocationType type;

    private int x;
    private int y;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public Location() {}

    public Location(String name, LocationType type, Country country) {
        this.name = name;
        this.type = type;
        this.country = country;
    }

    public int getX() {
        return x;
    }

    private int getY() {
        return y;
    }

    public int distanceTo(Location other) {
        int dx = other.getX() - this.getX();
        int dy = other.getY() - this.getY();
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

}
