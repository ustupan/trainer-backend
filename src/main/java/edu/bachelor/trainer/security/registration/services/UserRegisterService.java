package edu.bachelor.trainer.security.registration.services;

import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;

public interface UserRegisterService {
    User registerNewUserAccount(UserDto accountDto);
}
