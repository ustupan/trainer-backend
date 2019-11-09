package edu.bachelor.trainer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@Table(name = "Trainers")
@Entity
@Getter
@Setter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "trainers")
    @Column
    private Set<Athlete> athletes;

    @OneToMany(mappedBy = "trainerOwner")
    @Column
    private Set<Calendar> calendars;

}
