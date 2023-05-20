package fhtw.swen2.duelli.duvivie.swen2project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "Log")
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "log_id")
    public int log_id;

    @Column(name = "fk_tours_id")
    public int tour_id;

    @Column(name = "starting_time", nullable = false)
    public Timestamp starting_time;

    @Column(name="comment", nullable = false)
    public String comment;

    @Column(name="difficulty", nullable = false)
    public int difficulty;

    @Column(name="total_time", nullable = false)
    public int total_time;

    @Column(name="rating", nullable = false)
    public int rating;

    @Column(name="picture_path", nullable = false)
    public String picture_path;


}
