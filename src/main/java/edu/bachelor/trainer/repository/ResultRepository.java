package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Set<Result>> getAllByAthleteId(Long athlete_id);
}
