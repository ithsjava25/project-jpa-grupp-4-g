package org.example.service;

import org.example.EventType;

import java.math.BigDecimal;

public record EventResult(
    EventType type,
    String description,
    BigDecimal amount
) {

    public static EventResult penalty(String description, BigDecimal amount) {
        return new EventResult(org.example.EventType.PENALTY, description, amount.negate());
    }

    public static EventResult bonus(String description, BigDecimal amount) {
        return new EventResult(EventType.BONUS, description, amount);
    }
}

