package edu.bachelor.trainer.trainer.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainingDayDto {

    private Long id;

    private String title;

    private String description;

    private Date trainingDate;

    private String note;

    private Integer motivationLevel;

    private Integer dispositionLevel;

}
