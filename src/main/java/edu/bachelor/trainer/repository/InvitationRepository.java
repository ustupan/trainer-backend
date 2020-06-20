package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Set<Invitation>> getAllByReceiverUsername(String receiverUsername);
}
