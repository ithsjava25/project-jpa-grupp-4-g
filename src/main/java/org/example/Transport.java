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

    public Transport(TransportType type, String costPerMove) {
        this.type = type;
        this.costPerMove = new BigDecimal(costPerMove);
        switch (type){
            case BUSS ->  {
                this.diceCount = 1;
                this.diceSides = 6;
            }
            case TRAIN ->   {
                this.diceCount = 2;
                this.diceSides = 6;
            }
            case AIRPLANE ->   {
                this.diceCount = 3;
                this.diceSides = 6;
            }
        }
    }

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
