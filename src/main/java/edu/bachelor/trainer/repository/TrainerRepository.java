package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<User, Long> {

}
