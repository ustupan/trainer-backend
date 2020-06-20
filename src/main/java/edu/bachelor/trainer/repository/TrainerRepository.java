package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> getTrainerByUser_Username(String username);

}
