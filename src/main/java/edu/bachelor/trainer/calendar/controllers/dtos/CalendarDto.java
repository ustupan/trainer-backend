package edu.bachelor.trainer.calendar.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarDto {

    private Long id;

    private String title;

    private List<Long> trainingDaysIds;

    private String athleteUsername;

    private Long trainerId;
}
