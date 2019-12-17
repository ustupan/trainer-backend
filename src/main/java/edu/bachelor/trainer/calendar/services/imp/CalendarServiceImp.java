package edu.bachelor.trainer.calendar.services.imp;

import edu.bachelor.trainer.calendar.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.calendar.controllers.dtos.TrainingDayDto;
import edu.bachelor.trainer.calendar.services.CalendarService;
import edu.bachelor.trainer.common.exceptions.AthleteNotExistException;
import edu.bachelor.trainer.common.exceptions.CalendarExistException;
import edu.bachelor.trainer.common.exceptions.CalendarNotExistException;
import edu.bachelor.trainer.common.exceptions.TrainerNotExistException;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.CalendarRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Calendar;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.repository.entities.TrainingDay;
import edu.bachelor.trainer.security.JwtClaims;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                filter(athlete -> athlete.getId().equals(calendarDto.getAthleteId())).collect(Collectors.toSet());

        if(filtered.isEmpty()) {
            throw new AthleteNotExistException("No such athlete!");
        }
        Athlete athlete = filtered.iterator().next();
        long check = athlete.getCalendars().stream().filter(calendar -> calendar.getTrainer().getId().equals(trainer.getId())).count();
        if (check > 0) throw new CalendarExistException("Calendar already exist!");

        Calendar calendar = new Calendar();
        calendar.setTrainer(trainer);
        calendar.setAthlete(filtered.iterator().next());
        calendar.setTitle(calendarDto.getTitle());

        return calendarRepository.save(calendar);
    }



    @Override
    public CalendarDto getCalendarByAthleteId(Long athleteId, String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);
        final Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(jwtClaims.getUserUsername());
        if (!findTrainer.isPresent()) {
            throw new TrainerNotExistException("No such trainer!");
        }
        Trainer trainer = findTrainer.get();

        Set<Athlete> athletes = trainer.getAthletes();
        Set<Athlete> filtered = athletes.stream().
                filter(athlete -> athlete.getId().equals(athleteId)).collect(Collectors.toSet());

        if(filtered.isEmpty()) {
            throw new AthleteNotExistException("No such athlete!");
        }

        Optional<Calendar> findCalendar = calendarRepository.getByAthleteIdAndTrainerId(athleteId,trainer.getId());

        if(!findCalendar.isPresent()){
            throw new CalendarNotExistException("No calendar for an athlete!");
        }

        return calendarDtoMapper(findCalendar.get());
    }

    private Set<TrainingDayDto> trainingDayDtos(Set<TrainingDay> trainingDays){
        Stream<TrainingDayDto> trainingDayDtoStream = trainingDays.stream().map(trainingDay -> {
            Long id = trainingDay.getId();
            String title = trainingDay.getTitle();
            String description = trainingDay.getDescription();
            Date trainingDate = trainingDay.getTrainingDate();
            String note = trainingDay.getNote();
            Integer motivationLevel = trainingDay.getMotivationLevel();
            Integer dispositionLevel = trainingDay.getDispositionLevel();
            Long calendarId = trainingDay.getCalendar().getId();
            return new TrainingDayDto(id, title, description, trainingDate, note, motivationLevel, dispositionLevel, calendarId);
        });
        return trainingDayDtoStream.collect(Collectors.toSet());
    }

    private CalendarDto calendarDtoMapper(Calendar calendar){
        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setId(calendar.getId());
        calendarDto.setTitle(calendar.getTitle());
        calendarDto.setTrainingDays(trainingDayDtos(calendar.getTrainingDays()));

        return calendarDto;
    }

}
