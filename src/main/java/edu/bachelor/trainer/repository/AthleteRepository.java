package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Athlete;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
