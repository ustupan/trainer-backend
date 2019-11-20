package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
