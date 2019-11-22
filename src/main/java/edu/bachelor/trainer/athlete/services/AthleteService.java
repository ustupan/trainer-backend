package edu.bachelor.trainer.athlete.services;

import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface AthleteService {
    Athlete addTrainer(String trainerUsername, String jwtToken);
    Set<Trainer> getAllTrainers(String jwtToken);
}
