package edu.bachelor.trainer.calendar.services.imp;

import edu.bachelor.trainer.calendar.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.calendar.exceptions.AthleteNotExistException;
import edu.bachelor.trainer.calendar.exceptions.TrainerNotExistException;
import edu.bachelor.trainer.calendar.services.CalendarService;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.CalendarRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Calendar;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.security.JwtClaims;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CalendarServiceImp implements CalendarService {

    private final TrainerRepository trainerRepository;
    private final AthleteRepository athleteRepository;
    private final CalendarRepository calendarRepository;


    CalendarServiceImp(TrainerRepository trainerRepository,
                       AthleteRepository athleteRepository,
                       CalendarRepository calendarRepository){
        this.trainerRepository = trainerRepository;
        this.athleteRepository = athleteRepository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public Calendar createCalendar(CalendarDto calendarDto, String jwtToken) {

        JwtClaims jwtClaims = new JwtClaims(jwtToken);

        final Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(jwtClaims.getUserUsername());

        if (!findTrainer.isPresent()) {
            throw new TrainerNotExistException("No such trainer!");
        }

        Trainer trainer = findTrainer.get();

        Set<Athlete> athletes = trainer.getAthletes();

        Set<Athlete> filtered = athletes.stream().
                filter(athlete -> athlete.getUser().getUsername().equals(calendarDto.getAthleteUsername())).collect(Collectors.toSet());

        if(filtered.isEmpty()) {
            throw new AthleteNotExistException("No such athlete!");
        }

        Calendar calendar = new Calendar();
        calendar.setTrainer(trainer);
        calendar.setAthlete(filtered.iterator().next());
        calendar.setTitle(calendarDto.getTitle());

        return calendarRepository.save(calendar);
    }
}
