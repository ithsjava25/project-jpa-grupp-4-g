package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private int distance_per_turn;
    private BigDecimal cost_per_move;

    public Transport(){}

    public Transport(String type, int distance_per_turn, BigDecimal cost_per_move) {
        this.type = type;
        this.distance_per_turn = distance_per_turn;
        this.cost_per_move = cost_per_move;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
}
