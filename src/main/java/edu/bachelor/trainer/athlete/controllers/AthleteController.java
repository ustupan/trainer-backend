package edu.bachelor.trainer.athlete.controllers;


import edu.bachelor.trainer.athlete.services.AthleteService;
import edu.bachelor.trainer.configuration.SecurityConstants;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Trainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/athlete")  // TODO dodac do security config
public class AthleteController {

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    private final AthleteService athleteService;

    @PutMapping(value = "/addTrainer")
    public ResponseEntity addTrainer(@Valid @RequestBody String trainerUsername, BindingResult bindingResult, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Athlete athlete = athleteService.addTrainer(trainerUsername, JwtToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
