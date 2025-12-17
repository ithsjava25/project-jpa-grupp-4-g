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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
