package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Athlete;

import edu.bachelor.trainer.repository.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.Set;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    Optional<Athlete> getAthleteByUserId(Long userId);

    Optional<Athlete> getAthleteByUser_Username(String username);

}
