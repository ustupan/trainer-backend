package edu.bachelor.trainer.calendar.controllers;


import edu.bachelor.trainer.calendar.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.calendar.controllers.dtos.TrainingDayDto;
import edu.bachelor.trainer.calendar.exceptions.CalendarNotExistException;
import edu.bachelor.trainer.calendar.services.CalendarService;
import edu.bachelor.trainer.calendar.services.TrainingDayService;
import edu.bachelor.trainer.repository.entities.Calendar;
import edu.bachelor.trainer.repository.entities.TrainingDay;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/calendar")  // TODO dodac do security config
public class CalendarController {

    private final CalendarService calendarService;
    private final TrainingDayService trainingDayService;

    public CalendarController(CalendarService calendarService, TrainingDayService trainingDayService) {
        this.calendarService = calendarService;
        this.trainingDayService = trainingDayService;
    }

    @PostMapping(value = "/createCalendar")
    public ResponseEntity createController(@Valid @RequestBody CalendarDto calendarDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Calendar createdCalendar = calendarService.createCalendar(calendarDto);
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
}
