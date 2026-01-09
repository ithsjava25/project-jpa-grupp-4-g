package org.example;

import jakarta.persistence.*;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "continent_id", nullable = false)
    private Continent continent;

    public Country() {}

    public Country(String name, Continent continent) {
        this.name = name;
        this.continent = continent;
    }

    public String getName(){
        return name;
    }
}
