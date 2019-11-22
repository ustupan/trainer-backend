package edu.bachelor.trainer.trainer.services;

import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.trainer.controllers.dtos.AthleteDto;

import java.util.Set;

public interface TrainerService {

    Trainer addAthlete(String athleteUsername,String jwtToken);
    Trainer getTrainerByUserUsername(String username);
    Set<AthleteDto> getAllTrainerAthletes(String jwtToken);
}

