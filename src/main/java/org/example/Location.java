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

    public Location(String name, LocationType type, Country country, int x, int y) {
        this.name = name;
        this.type = type;
        this.country = country;
        this.x = x;
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }


    public Long getId() {
        return id;
    }
}
