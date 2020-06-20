package edu.bachelor.trainer.security.registration.services;

import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;

public interface AthleteRegisterService {
    Athlete registerNewAthleteAccount(UserDto userDto);
}
