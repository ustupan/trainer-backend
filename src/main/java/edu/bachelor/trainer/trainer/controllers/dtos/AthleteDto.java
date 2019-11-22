package edu.bachelor.trainer.trainer.controllers.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AthleteDto {

    private Long id;

    private String userName;

    private Set<ResultDto> results;

    private CalendarDto calendar;
}
