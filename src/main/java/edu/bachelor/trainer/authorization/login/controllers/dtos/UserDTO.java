package edu.bachelor.trainer.authorization.login.controllers.dtos;

import edu.bachelor.trainer.common.Gender;
import edu.bachelor.trainer.repository.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;

    private String login;

    private String email;

    private String password;

    private Gender gender;

    private Set<Role> roles;

}
