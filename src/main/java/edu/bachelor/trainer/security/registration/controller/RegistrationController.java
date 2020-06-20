package edu.bachelor.trainer.security.registration.controller;

import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.security.exceptions.AthleteRegisterException;
import edu.bachelor.trainer.security.exceptions.UndefinedRoleException;
import edu.bachelor.trainer.security.exceptions.UsernameExistsException;
import edu.bachelor.trainer.security.registration.services.AthleteRegisterService;
import edu.bachelor.trainer.security.registration.services.TrainerRegisterService;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final AthleteRegisterService athleteRegisterService;
    private final TrainerRegisterService trainerRegisterService;

    public RegistrationController(AthleteRegisterService athleteRegisterService, TrainerRegisterService trainerRegisterService) {
        this.athleteRegisterService = athleteRegisterService;
        this.trainerRegisterService = trainerRegisterService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity registerUserAccount(@Valid @RequestBody UserDto accountDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(accountDto.getRoleName().contains("ATHLETE")){
            return registerAthlete(accountDto);
        } else{
            return registerTrainer(accountDto);
        }
    }

    private ResponseEntity registerAthlete(UserDto accountDto) {
        try {
            Athlete registeredAthlete = createAthleteAccount(accountDto);
        }catch (UsernameExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }catch (UndefinedRoleException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Undefined role");
        }catch (AthleteRegisterException e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not create an account");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity registerTrainer(UserDto accountDto) {
        try {
            Trainer registeredTrainer = createTrainerAccount(accountDto);
        }catch (UsernameExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }catch (UndefinedRoleException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Undefined role");
        }catch (AthleteRegisterException e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not create an account");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Athlete createAthleteAccount(UserDto accountDto) {
        Athlete registered;
        registered = athleteRegisterService.registerNewAthleteAccount(accountDto);
        return registered;
    }

    private Trainer createTrainerAccount(UserDto accountDto){
        Trainer registered;
        registered = trainerRegisterService.registerNewTrainerAccount(accountDto);
        return registered;
    }
}
