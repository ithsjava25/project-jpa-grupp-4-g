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

    @ManyToOne(optional = false)
    private LocationLink locationLink;

    @ManyToOne(optional = false)
    private Transport transport;
}

