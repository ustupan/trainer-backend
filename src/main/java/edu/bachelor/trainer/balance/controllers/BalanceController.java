package edu.bachelor.trainer.balance.controllers;


import edu.bachelor.trainer.balance.controllers.dtos.ResultDto;
import edu.bachelor.trainer.balance.services.ResultService;
import edu.bachelor.trainer.calendar.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.common.exceptions.AthleteNotExistException;
import edu.bachelor.trainer.configuration.SecurityConstants;
import edu.bachelor.trainer.repository.entities.Result;
import edu.bachelor.trainer.security.JwtClaims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/balance")  // TODO dodac do security config
public class BalanceController {

    private final ResultService resultService;


    public BalanceController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping(value = "/addResult")
    public ResponseEntity createCalendar(@Valid @RequestBody ResultDto resultDto, BindingResult bindingResult , @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {


        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try{
            Result result = resultService.createResult(resultDto, JwtToken);
        }catch (AthleteNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("daasasd");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getAllAthleteResults")
    public ResponseEntity athleteList(@Valid @RequestBody Long athleteId, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {
        return ResponseEntity.ok().body(resultService.getAllResultsByAthleteId(athleteId, JwtToken));
    }

    //get my results...


}
