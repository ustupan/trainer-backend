package edu.bachelor.trainer.trainer.services.imp;

import edu.bachelor.trainer.calendar.exceptions.TrainerNotExistException;
import edu.bachelor.trainer.common.exceptions.AthleteNotExistException;
import edu.bachelor.trainer.common.exceptions.TrainerAlreadyHasAthleteException;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.security.JwtClaims;
import edu.bachelor.trainer.trainer.services.TrainerService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TrainerServiceImp implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final AthleteRepository athleteRepository;

    public TrainerServiceImp(TrainerRepository trainerRepository, AthleteRepository athleteRepository) {
        this.trainerRepository = trainerRepository;
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Trainer addAthlete(String athleteUsername, String jwtToken) {

        JwtClaims jwtClaims = new JwtClaims(jwtToken);
        Optional<Athlete> findAthlete = athleteRepository.getAthleteByUser_Username(athleteUsername);

        if (!findAthlete.isPresent()) {
            throw new AthleteNotExistException("No such athlete!");
        }

        Trainer trainer = getTrainerByUserUsername(jwtClaims.getUserUsername());
        Set<Athlete> athletes = trainer.getAthletes();
        Set<Athlete> filtered = athletes.stream().
                filter(athlete -> athlete.getUser().getUsername().equals(athleteUsername)).collect(Collectors.toSet());
        if(!filtered.isEmpty()) {
            throw new TrainerAlreadyHasAthleteException("Trainer already has athlete!");
        }

        trainer.addAthlete(findAthlete.get());

        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer getTrainerByUserUsername(String username) {
        Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(username);
        if (!findTrainer.isPresent()) {
            throw new TrainerNotExistException("No such trainer!");
        }
        return findTrainer.get();
    }
}
