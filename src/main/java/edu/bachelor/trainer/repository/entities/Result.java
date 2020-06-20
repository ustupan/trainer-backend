package edu.bachelor.trainer.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Table(name="Results")
@Entity
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String discipline;
    @Column
    private String description;
    @Column
    private Date resultDate; //jak cos dac w stringu
    @ManyToOne
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;
    @Column
    private String unit;
    @Column
    private Integer motivationLevel;
    @Column
    private Integer dispositionLevel;
    @Column
    private Long value;
}
