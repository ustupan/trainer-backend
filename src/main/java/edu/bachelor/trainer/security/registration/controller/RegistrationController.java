package edu.bachelor.trainer.security.registration.controller;

import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.security.exceptions.UndefinedRoleException;
import edu.bachelor.trainer.security.exceptions.UsernameExistsException;
import edu.bachelor.trainer.security.registration.services.AthleteRegisterService;
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

    private String errorResponse = "";
    private final AthleteRegisterService athleteRegisterService;

    public RegistrationController(AthleteRegisterService athleteRegisterService) {
        this.athleteRegisterService = athleteRegisterService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity registerUserAccount(@Valid @RequestBody UserDto accountDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Athlete registeredAthlete = createAthleteAccount(accountDto);

        if (registeredAthlete == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private Athlete createAthleteAccount(UserDto userDto) {
        Athlete registered;
        try {
            registered = athleteRegisterService.registerNewAthleteAccount(userDto);
        } catch (UsernameExistsException e){
            errorResponse = "Username already exists";
            return null;
        } catch (UndefinedRoleException e) {
            errorResponse = "Undefined role exception";
            return null;
        }
        return registered;
    }


}
