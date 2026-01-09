package org.example;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Continent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "continent")
    private List<Country> countries;

    public Continent(){}

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }




}
