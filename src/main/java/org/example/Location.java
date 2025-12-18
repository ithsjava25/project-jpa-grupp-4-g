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

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public Location() {}

    public Location(String name, LocationType type, Country country) {
        this.name = name;
        this.type = type;
        this.country = country;
    }



}
