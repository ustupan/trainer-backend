package edu.bachelor.trainer.trainer.controllers;


import edu.bachelor.trainer.configuration.SecurityConstants;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.trainer.services.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/trainer")  // TODO dodac do security config
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PutMapping(value = "/addAthlete")
    public ResponseEntity addAthlete(@Valid @RequestBody String athleteUsername, BindingResult bindingResult, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Trainer trainer = trainerService.addAthlete(athleteUsername, JwtToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
