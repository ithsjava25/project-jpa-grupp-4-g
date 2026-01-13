package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.Traveler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

public class PlayerEventService {

    private final Random random = new Random();

    public PlayerEventService(EntityManager em) {
    }

    /**
     * Körs efter ett avslutat drag (Journey)
     * Returnerar ett EventResult som GUI / CLI kan visa
     */
    public Optional<EventResult> applyEndOfTurnEvents(Traveler traveler) {

        // penalty har högre prio än bonus
        if (random.nextDouble() < 0.05) {
            return Optional.of(applyPenalty(traveler));
        }

        if (random.nextDouble() < 0.10) {
            return Optional.of(applyBonus(traveler));
        }

        return Optional.empty();
    }

    /* ======================
       PENALTY
       ====================== */

    private EventResult applyPenalty(Traveler traveler) {

        int roll = random.nextInt(6) + 1;
        BigDecimal amount;

        String description = switch (roll) {
            case 1 -> {
                amount = new BigDecimal("50");
                yield "you fell and hurt yourself (-50 credits)";
            }
            case 2 -> {
                amount = new BigDecimal("100");
                yield "partied too hard last night (-100 credits)";
            }
            case 3 -> {
                amount = new BigDecimal("150");
                yield "scammed by a local (-150 credits)";
            }
            case 4 -> {
                amount = new BigDecimal("200");
                yield "transport broke down (-200 credits)";
            }
            case 5 -> {
                amount = new BigDecimal("250");
                yield "huge phone bill (-250 credits)";
            }
            case 6 -> {
                amount = new BigDecimal("500");
                yield "luxury hotel disaster (-500 credits)";
            }
            default -> {
                amount = BigDecimal.ZERO;
                yield "no penalty";
            }
        };

        traveler.pay(amount);

        return EventResult.penalty(description, amount);
    }

    /* ======================
       BONUS
       ====================== */

    private EventResult applyBonus(Traveler traveler) {

        int roll = random.nextInt(6) + 1;
        BigDecimal amount;

        String description = switch (roll) {
            case 1 -> {
                amount = new BigDecimal("50");
                yield "found some spare change (+50 credits)";
            }
            case 2 -> {
                amount = new BigDecimal("100");
                yield "helped someone and got rewarded (+100 credits)";
            }
            case 3 -> {
                amount = new BigDecimal("250");
                yield "temporary job success (+250 credits)";
            }
            case 4 -> {
                amount = new BigDecimal("400");
                yield "parents sent you money (+400 credits)";
            }
            case 5 -> {
                amount = new BigDecimal("500");
                yield "birthday gifts (+500 credits)";
            }
            case 6 -> {
                amount = new BigDecimal("1000");
                yield "won the lottery (+1000 credits)";
            }
            default -> {
                amount = BigDecimal.ZERO;
                yield "no bonus";
            }
        };

        traveler.addMoney(amount);

        return EventResult.bonus(description, amount);
    }
}

