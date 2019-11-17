package edu.bachelor.trainer.security.registration.services.imp;

import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.security.registration.services.TrainerRegisterService;
import edu.bachelor.trainer.security.registration.services.UserRegisterService;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class TrainerRegisterServiceImp implements TrainerRegisterService {


    private final TrainerRepository trainerRepository;
    private final UserRegisterService userRegisterService;

    public TrainerRegisterServiceImp(TrainerRepository trainerRepository, UserRegisterService userRegisterService) {
        this.trainerRepository = trainerRepository;
        this.userRegisterService = userRegisterService;
    }

    @Override
    @Transactional
    public Trainer registerNewTrainerAccount(UserDto userDto) {

        User registeredUser = createUserAccount(userDto);
        if (registeredUser == null){
            throw new RuntimeException();
        }
        Trainer trainer = new Trainer();
        trainer.setUser(registeredUser);
        return trainerRepository.save(trainer);
    }

    private User createUserAccount(UserDto userDto) {
        User registered;
        registered = userRegisterService.registerNewUserAccount(userDto);
        return registered;
    }
}
