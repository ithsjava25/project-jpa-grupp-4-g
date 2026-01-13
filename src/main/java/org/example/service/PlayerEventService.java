package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.Traveler;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.math.BigDecimal;

public class PlayerEventService {

    private final EntityManager em;
    private ListView<String> guiLog;

    public PlayerEventService(EntityManager em) {
        this.em = em;
    }

    /**
     * Optional: attach a GUI log to display events
     */
    public void setGuiLog(ListView<String> guiLog) {
        this.guiLog = guiLog;
    }

    private void log(String msg) {
        System.out.println(msg);
        if (guiLog != null) {
            Platform.runLater(() -> guiLog.getItems().add(msg));
        }
    }

    /**
     * Används efter varje turn
     */
    public void applyEndOfTurnEvents(Traveler traveler) {

        // 1️⃣ slumpmässig penalty
        if (Math.random() < 0.05) {
            int amount = randomPenalty(traveler);
            traveler.addCredits(-amount);
            log(traveler.getPlayerName() + " fick en penalty: -" + amount + " credits");
        }

        // 2️⃣ slumpmässig bonus
        if (Math.random() < 0.10) {
            int amount = randomBonus(traveler);
            traveler.addCredits(amount);
            log(traveler.getPlayerName() + " fick en bonus: +" + amount + " credits");
        }
    }

    private int randomPenalty(Traveler traveler) {
        int n = (int) (6 * Math.random()) + 1;
        return switch (n) {
            case 1 -> 50;
            case 2 -> 100;
            case 3 -> 150;
            case 4 -> 200;
            case 5 -> 250;
            case 6 -> 500;
            default -> 0;
        };
    }

    private int randomBonus(Traveler traveler) {
        int n = (int) (6 * Math.random()) + 1;
        return switch (n) {
            case 1 -> 50;
            case 2 -> 100;
            case 3 -> 250;
            case 4 -> 400;
            case 5 -> 500;
            case 6 -> 1000;
            default -> 0;
        };
    }
}
