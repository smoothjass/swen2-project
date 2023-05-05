package fhtw.swen2.duelli.duvivie.swen2project.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "transport_types")
public class TransportType {
    @Id
    @Column(name = "transport_type_id", unique = true)
    private Integer transport_type_id;

    @Column(name = "type", nullable = false)
    private String type;
}
