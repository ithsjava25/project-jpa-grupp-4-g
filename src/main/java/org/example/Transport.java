package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(name = "distance_per_turn")
    private int distancePerTurn;

    @Column(name = "cost_per_move")
    private BigDecimal costPerMove;

    public Transport(){}

    public Transport(String type, int distancePerTurn, BigDecimal costPerMove) {
        this.type = type;
        this.distancePerTurn = distancePerTurn;
        this.costPerMove = costPerMove;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
}
