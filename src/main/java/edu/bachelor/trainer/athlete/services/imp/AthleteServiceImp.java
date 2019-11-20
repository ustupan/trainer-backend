package edu.bachelor.trainer.athlete.services.imp;

import edu.bachelor.trainer.athlete.exceptions.AthleteAlreadyHasTrainerException;
import edu.bachelor.trainer.athlete.services.AthleteService;
import edu.bachelor.trainer.calendar.exceptions.TrainerNotExistException;
import edu.bachelor.trainer.common.exceptions.AthleteNotExistException;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.security.JwtClaims;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AthleteServiceImp implements AthleteService {

    private final TrainerRepository trainerRepository;
    private final AthleteRepository athleteRepository;

    public AthleteServiceImp(TrainerRepository trainerRepository, AthleteRepository athleteRepository) {
        this.trainerRepository = trainerRepository;
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Athlete addTrainer(String trainerUsername, String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);
        final Optional<Athlete> findAthlete = athleteRepository.getAthleteByUser_Username(jwtClaims.getUserUsername());

        Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(trainerUsername);

        if (!findAthlete.isPresent()) {
            throw new AthleteNotExistException("No such athlete!");
        }
        if (!findTrainer.isPresent()) {
            throw new TrainerNotExistException("No such trainer!");
        }

        Athlete athlete = findAthlete.get();
        Set<Trainer> trainers = athlete.getTrainers();
        Set<Trainer> filtered = trainers.stream().
                filter(trainer -> trainer.getUser().getUsername().equals(trainerUsername)).collect(Collectors.toSet());
        if(!filtered.isEmpty()) {
            throw new AthleteAlreadyHasTrainerException("Athlete already has trainer!");
        }
        athlete.addTrainer(findTrainer.get());

        return athleteRepository.save(athlete);
    }
}
