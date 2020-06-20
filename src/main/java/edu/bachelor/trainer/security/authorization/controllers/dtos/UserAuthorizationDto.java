package edu.bachelor.trainer.security.authorization.controllers.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthorizationDto {

    private String username;
    private String password;

}
