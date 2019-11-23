package edu.bachelor.trainer.athlete.services;

import edu.bachelor.trainer.athlete.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.athlete.controllers.dtos.ResultDto;
import edu.bachelor.trainer.athlete.controllers.dtos.TrainerDto;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface AthleteService {
    Athlete addTrainer(String trainerUsername, String jwtToken);
    Set<TrainerDto> getAllTrainers(String jwtToken);
    Set<ResultDto> getAllAthleteResults(String jwtToken);
    Set<CalendarDto> getAllAthleteCalendars(String jwtToken);
}
