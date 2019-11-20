package edu.bachelor.trainer.athlete.services;

import edu.bachelor.trainer.repository.entities.Athlete;
import org.springframework.stereotype.Service;


public interface AthleteService {
    Athlete addTrainer(String trainerUsername, String jwtToken);
}
