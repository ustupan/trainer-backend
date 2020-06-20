package edu.bachelor.trainer.calendar.controllers;


import edu.bachelor.trainer.athlete.controllers.dtos.AthleteDto;
import edu.bachelor.trainer.calendar.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.calendar.controllers.dtos.TrainingDayDto;
import edu.bachelor.trainer.calendar.services.CalendarService;
import edu.bachelor.trainer.calendar.services.TrainingDayService;
import edu.bachelor.trainer.common.exceptions.CalendarNotExistException;
import edu.bachelor.trainer.common.exceptions.TrainingDayNotExistException;
import edu.bachelor.trainer.configuration.SecurityConstants;
import edu.bachelor.trainer.repository.entities.Calendar;
import edu.bachelor.trainer.repository.entities.Invitation;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.repository.entities.TrainingDay;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final TrainingDayService trainingDayService;

    public CalendarController(CalendarService calendarService, TrainingDayService trainingDayService) {
        this.calendarService = calendarService;
        this.trainingDayService = trainingDayService;
    }

    @PostMapping(value = "/createCalendar")
    public ResponseEntity createCalendar(@Valid @RequestBody CalendarDto calendarDto, BindingResult bindingResult, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Calendar createdCalendar = calendarService.createCalendar(calendarDto, JwtToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/addTrainingDay")
    public ResponseEntity addTrainingDay(@Valid @RequestBody TrainingDayDto trainingDayDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            TrainingDay trainingDay = trainingDayService.createTrainingDay(trainingDayDto);
        }catch (CalendarNotExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Calendar not exist");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/editTrainingDay")
    public ResponseEntity editTrainingDay(@Valid @RequestBody TrainingDayDto trainingDayDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            TrainingDay trainingDay = trainingDayService.editTrainingDay(trainingDayDto);
        }catch (CalendarNotExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Calendar not exist");
        }catch (TrainingDayNotExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Training day not exist");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/getCalendarByAthleteId")
    public ResponseEntity getCalendarByAthleteId(@Valid @RequestBody AthleteDto athleteDto, BindingResult bindingResult, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok().body(calendarService.getCalendarByAthleteId(athleteDto.getId(),JwtToken));
    }

    @PostMapping(value = "/getCalendarById")
    public ResponseEntity getCalendarById(@Valid @RequestBody CalendarDto calendarDto, BindingResult bindingResult, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok().body(calendarService.getCalendarById(calendarDto.getId(),JwtToken));
    }

}
