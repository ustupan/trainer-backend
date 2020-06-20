package edu.bachelor.trainer.security.registration.services;

import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;

public interface TrainerRegisterService {
    Trainer registerNewTrainerAccount(UserDto userDto);
}
