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

    @Column(name = "cost_per_move",nullable = false)
    private BigDecimal costPerMove;

    @Column(name = "dice_count",nullable = false)
    private int diceCount;

    @Column(name = "dice_sides",nullable = false)
    private int diceSides;

    public Transport(){}

    public int rollDistance() {
        Random r = new Random();
        int total = 0;
        for (int i = 0; i < diceCount; i++) {
            total += r.nextInt(diceSides) + 1;
        }
        return total;
    }

    public BigDecimal getCostPerMove() {
        return costPerMove;
    }

    public int getDiceCount(){
        return diceCount;
    }

    public String getType() {
        return type.toString();
    }

}
