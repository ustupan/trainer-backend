package edu.bachelor.trainer.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "trainer")
    @Column
    private Set<Calendar> calendars;

    public void addAthlete(Athlete athlete){
        this.athletes.add(athlete);
        athlete.getTrainers().add(this);
    }

}
