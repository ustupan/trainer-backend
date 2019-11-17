package edu.bachelor.trainer.security.registration.services.imp;


import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.security.registration.services.AthleteRegisterService;
import edu.bachelor.trainer.security.registration.services.UserRegisterService;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class AthleteRegisterServiceImp implements AthleteRegisterService {


    private final AthleteRepository athleteRepository;
    private final UserRegisterService userRegisterService;

    public AthleteRegisterServiceImp(AthleteRepository athleteRepository, UserRegisterService userRegisterService) {
        this.athleteRepository = athleteRepository;
        this.userRegisterService = userRegisterService;
    }

    @Override
    @Transactional
    public Athlete registerNewAthleteAccount(UserDto userDto) {

        User registeredUser = createUserAccount(userDto);
        if (registeredUser == null){
            throw new RuntimeException();
        }
        Athlete athlete = new Athlete();
        athlete.setUser(registeredUser);
        return athleteRepository.save(athlete);
    }

    private User createUserAccount(UserDto userDto) {
        User registered;
        registered = userRegisterService.registerNewUserAccount(userDto);
        return registered;
    }
}
