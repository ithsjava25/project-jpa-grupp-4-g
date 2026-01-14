package org.example;

import jakarta.persistence.*;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"location_link_id", "transport_id"}
    )
)
public class TransportLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * vilken rutt det gäller
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_link_id", nullable = false)
    private LocationLink locationLink;

    /**
     * vilket transportmedel som är tillåtet på rutten
     */

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;

    public TransportLink() {}

    public TransportLink(LocationLink locationLink, Transport transport) {
        this.locationLink = locationLink;
        this.transport = transport;
    }

    public Long getId() {
        return id;
    }

    public Transport getTransport() {
        return transport;
    }

}

