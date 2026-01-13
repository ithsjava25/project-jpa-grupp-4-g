package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "turn_event",
    indexes = {
        @Index(name = "idx_event_traveler", columnList = "traveler_id"),
        @Index(name = "idx_event_journey", columnList = "journey_id")
    }
)
public class TurnEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // vem drabbas/får bonus
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "traveler_id", nullable = false)
    private Traveler traveler;

    // vilket drag (turn) det hör till
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "journey_id", nullable = false)
    private Journey journey;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 20)
    private EventType type;

    // alltid positivt belopp; sign bestäms av type
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected TurnEvent() {}

    public TurnEvent(
        Traveler traveler,
        Journey journey,
        EventType type,
        BigDecimal amount,
        String message
    ) {
        this.traveler = traveler;
        this.journey = journey;
        this.type = type;
        this.amount = amount;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Traveler getTraveler() { return traveler; }
    public Journey getJourney() { return journey; }
    public EventType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getMessage() { return message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
