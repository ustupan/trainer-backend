package edu.bachelor.trainer.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Table(name="Athletes")
@Entity
@Getter
@Setter
public class Athlete {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(name = "athlete_trainer",
            joinColumns = @JoinColumn(name = "athlete_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    @Column
    private Set<Trainer> trainers;
    @OneToMany(mappedBy = "athlete")
    private Set<Result> results;
    @OneToMany(mappedBy = "athlete")
    private Set<Calendar> calendars;
}
