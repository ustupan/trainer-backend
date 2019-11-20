package edu.bachelor.trainer.trainer.services;

import edu.bachelor.trainer.repository.entities.Trainer;

public interface TrainerService {

    Trainer addAthlete(String athleteUsername,String jwtToken);
}
