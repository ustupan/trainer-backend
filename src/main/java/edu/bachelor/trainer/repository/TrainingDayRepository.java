package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.TrainingDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingDayRepository extends JpaRepository<TrainingDay, Long> {


}
