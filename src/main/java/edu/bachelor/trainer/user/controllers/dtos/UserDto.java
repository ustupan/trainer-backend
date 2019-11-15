package edu.bachelor.trainer.user.controllers.dtos;

import edu.bachelor.trainer.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    public UserDto() {
    }

    private Long id;

    private String username;

    private String email;

    private String password;

    private Gender gender;

    private String roleName;

}