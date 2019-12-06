package edu.bachelor.trainer.calendar.controllers.dtos;

import edu.bachelor.trainer.calendar.controllers.dtos.TrainingDayDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarDto {

    private Long id;

    private String title;

    private Set<TrainingDayDto> trainingDays;

    private Long athleteId;

    private Long trainerId;
}
