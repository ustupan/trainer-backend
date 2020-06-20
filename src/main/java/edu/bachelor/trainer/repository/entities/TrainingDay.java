package edu.bachelor.trainer.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Training_days")
@Entity
@Getter
@Setter
public class TrainingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Date trainingDate;
    @Column
    private String note;
    @Column
    private Integer motivationLevel;
    @Column
    private Integer dispositionLevel;
    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;
}
