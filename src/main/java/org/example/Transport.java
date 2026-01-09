package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Random;

@Entity
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransportType type;

    @Column(name = "distance_per_turn")
    private int distancePerTurn;

    @Column(name = "cost_per_move")
    private BigDecimal costPerMove;

    private int diceCount;
    private int diceSides;

    public Transport(){}

    public Transport(TransportType type, int distancePerTurn, BigDecimal costPerMove) {
        this.type = type;
        this.distancePerTurn = distancePerTurn;
        this.costPerMove = costPerMove;
    }

    public int rollDistance() {
        Random r = new Random();
        int total = 0;
        for (int i = 0; i < diceCount; i++) {
            total += r.nextInt(diceSides) + 1;
        }
        return total;
    }

    public TransportType getType(){
        return type;
    }
    public void setType(TransportType type){
        this.type = type;
    }
}
