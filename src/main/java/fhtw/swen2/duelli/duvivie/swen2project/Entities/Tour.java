package fhtw.swen2.duelli.duvivie.swen2project.Entities;

import jakarta.persistence.*;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @Column(name = "tour_id", unique = true)
    private Integer tour_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "from", nullable = false)
    private String from;

    @Column(name = "to", nullable = false)
    private String to;

    @ManyToOne
    @JoinColumn(name = "transport_type_id")
    private TransportType transportType;

    @Column(name = "distance", nullable = false)
    private Float distance;

    //@Column(name = "time", nullable = false, columnDefinition = "interval")
    //private Duration time;
}
