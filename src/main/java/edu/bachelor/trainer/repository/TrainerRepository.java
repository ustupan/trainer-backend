package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}
