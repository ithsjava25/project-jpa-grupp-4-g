package org.example.service;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import org.example.EventType;
import org.example.Traveler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlayerEventService {

    private ListView<String> guiLog;

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
     * skapar 0–2 events, uppdaterar traveler.money och returnerar event-data
     * (journeyservice persisterar dem kopplat till Journey)
     */
    public List<EventResult> applyEndOfTurnEvents(Traveler traveler) {
        List<EventResult> events = new ArrayList<>();

        // penalty 15%
        if (Math.random() < 0.15) {
            EventOutcome o = randomPenalty();
            BigDecimal amount = BigDecimal.valueOf(o.amount());
            traveler.deductMoney(amount);

            events.add(new EventResult(EventType.PENALTY, amount, o.message()));

            log("⚠ " + traveler.getPlayerName() + ": " + o.message()
                + " (-" + o.amount() + " credits)");
        }

        // bonus 15%
        if (Math.random() < 0.15) {
            EventOutcome o = randomBonus();
            BigDecimal amount = BigDecimal.valueOf(o.amount());
            traveler.addMoney(amount);

            events.add(new EventResult(EventType.BONUS, amount, o.message()));

            log("🎁 " + traveler.getPlayerName() + ": " + o.message()
                + " (+" + o.amount() + " credits)");
        }

        return events;
    }

    public record EventResult(EventType type, BigDecimal amount, String message) {}
    private record EventOutcome(int amount, String message) {}

    private EventOutcome randomPenalty() {
        int n = (int) (6 * Math.random()) + 1;
        return switch (n) {
            case 1 -> new EventOutcome(50,  "you fell and bruised, pay some fees");
            case 2 -> new EventOutcome(100, "you partied too hard last night, lost credits");
            case 3 -> new EventOutcome(150, "you got scammed by a local, what a shame");
            case 4 -> new EventOutcome(200, "your transport broke, need a mechanic");
            case 5 -> new EventOutcome(250, "you have a big phone bill, don't call that much");
            case 6 -> new EventOutcome(500, "really.. the ritz hotel");
            default -> new EventOutcome(0, "no penalties");
        };
    }

    private EventOutcome randomBonus() {
        int n = (int) (6 * Math.random()) + 1;
        return switch (n) {
            case 1 -> new EventOutcome(50,   "found some spare change");
            case 2 -> new EventOutcome(100,  "helped an old woman across the street");
            case 3 -> new EventOutcome(250,  "got a day job");
            case 4 -> new EventOutcome(400,  "your parents sent you some money");
            case 5 -> new EventOutcome(500,  "happy birthday!");
            case 6 -> new EventOutcome(1000, "won the lottery!");
            default -> new EventOutcome(0, "no bonus");
        };
    }

}
