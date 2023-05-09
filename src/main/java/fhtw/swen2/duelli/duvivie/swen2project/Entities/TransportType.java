package fhtw.swen2.duelli.duvivie.swen2project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "TransportType")
@Table(name = "transport_types")
public class TransportType {
    @Id
    @Column(name = "transport_type_id")
    public int transport_type_id;

    @Column(name = "type", nullable = false)
    public String type;
}
