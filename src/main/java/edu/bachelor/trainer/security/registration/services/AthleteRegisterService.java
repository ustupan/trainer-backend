package edu.bachelor.trainer.security.registration.services;

import edu.bachelor.trainer.athlete.controllers.dtos.AthleteDto;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;

public interface AthleteRegisterService {
    public Athlete registerNewAthleteAccount(UserDto userDto);
}
