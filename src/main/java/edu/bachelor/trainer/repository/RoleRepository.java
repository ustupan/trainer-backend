package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Role;
import edu.bachelor.trainer.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
