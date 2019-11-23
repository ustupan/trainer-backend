package edu.bachelor.trainer.athlete.controllers.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CalendarDto {

    private Long id;

    private String title;

    private Set<TrainingDayDto> trainingDays;

}