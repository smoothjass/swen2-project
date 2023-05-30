package fhtw.swen2.duelli.duvivie.swen2project.Entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Tour")
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "tour_id")
    public int tour_id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description", nullable = false)
    public String description;

    @Column(name = "point_a", nullable = false)
    public String from;

    @Column(name = "point_b", nullable = false)
    public String to;

    @ManyToOne
    @JoinColumn(name = "transport_type_id")
    public TransportType transportType;
    // so lassen

    @Column(name = "distance", nullable = false)
    public Float distance;

    @Column(name = "duration", nullable = false)
    public Integer duration;
}
