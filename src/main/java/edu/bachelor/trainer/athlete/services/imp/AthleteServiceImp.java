package edu.bachelor.trainer.athlete.services.imp;

import edu.bachelor.trainer.athlete.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.athlete.controllers.dtos.ResultDto;
import edu.bachelor.trainer.athlete.controllers.dtos.TrainerDto;
import edu.bachelor.trainer.athlete.controllers.dtos.TrainingDayDto;
import edu.bachelor.trainer.athlete.exceptions.AthleteAlreadyHasTrainerException;
import edu.bachelor.trainer.athlete.services.AthleteService;
import edu.bachelor.trainer.calendar.exceptions.TrainerNotExistException;
import edu.bachelor.trainer.common.exceptions.AthleteNotExistException;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.*;
import edu.bachelor.trainer.security.JwtClaims;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AthleteServiceImp implements AthleteService {

    private final TrainerRepository trainerRepository;
    private final AthleteRepository athleteRepository;

    public AthleteServiceImp(TrainerRepository trainerRepository, AthleteRepository athleteRepository) {
        this.trainerRepository = trainerRepository;
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Athlete addTrainer(String trainerUsername, String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);

        Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(trainerUsername);

        if (!findTrainer.isPresent()) {
            throw new TrainerNotExistException("No such trainer!");
        }

        Athlete athlete = getAthleteByToken(jwtToken);
        Set<Trainer> trainers = athlete.getTrainers();
        Set<Trainer> filtered = trainers.stream().
                filter(trainer -> trainer.getUser().getUsername().equals(trainerUsername)).collect(Collectors.toSet());
        if(!filtered.isEmpty()) {
            throw new AthleteAlreadyHasTrainerException("Athlete already has trainer!");
        }
        athlete.addTrainer(findTrainer.get());

        return athleteRepository.save(athlete);
    }

    @Override
    public Set<TrainerDto> getAllTrainers(String jwtToken) {

        Athlete athlete = getAthleteByToken(jwtToken);

        Stream<TrainerDto> trainerDtoStream = athlete.getTrainers().stream().map(trainer -> {
            Long id = trainer.getId();
            String username = trainer.getUser().getUsername();
            return new TrainerDto(id, username);
        });

        return trainerDtoStream.collect(Collectors.toSet());
    }

    @Override
    public Set<ResultDto> getAllAthleteResults(String jwtToken) {
        Athlete athlete = getAthleteByToken(jwtToken);
        return resultDtoMapper(athlete.getResults());
    }

    @Override
    public Set<CalendarDto> getAllAthleteCalendars(String jwtToken) {
        Athlete athlete = getAthleteByToken(jwtToken);
        return calendarDtosMapper(athlete.getCalendars(), athlete.getUser().getUsername());
    }

    private Athlete getAthleteByToken(String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);
        final Optional<Athlete> findAthlete = athleteRepository.getAthleteByUser_Username(jwtClaims.getUserUsername());
        if (!findAthlete.isPresent()) {
            throw new AthleteNotExistException("No such athlete!");
        }
        return findAthlete.get();
    }

    private Set<ResultDto> resultDtoMapper(Set<Result> results){
        Set<ResultDto> resultDtos = new HashSet<ResultDto>();

        Stream<ResultDto> resultDtoStream = results.stream().map(result -> {
            Long id = result.getId();
            String discipline = result.getDiscipline();
            String description = result.getDescription();
            Date resultDate = result.getResultDate();
            Long value = result.getValue();
            return new ResultDto(id,discipline,description,resultDate,value);
        });

        return resultDtoStream.collect(Collectors.toSet());
    }

    private Set<TrainingDayDto> trainingDayDtos(Set<TrainingDay> trainingDays){

        if (trainingDays.isEmpty()) return null;// todo ew rzucać wyjątek
        Stream<TrainingDayDto> trainingDayDtoStream = trainingDays.stream().map(trainingDay -> {
            Long id = trainingDay.getId();
            String title = trainingDay.getTitle();
            String description = trainingDay.getDescription();
            Date trainingDate = trainingDay.getTrainingDate();
            String note = trainingDay.getNote();
            Integer motivationLevel = trainingDay.getMotivationLevel();
            Integer dispositionLevel = trainingDay.getDispositionLevel();
            return new TrainingDayDto(id, title, description, trainingDate, note, motivationLevel, dispositionLevel);
        });
        return trainingDayDtoStream.collect(Collectors.toSet());
    }

    private Set<CalendarDto> calendarDtosMapper(Set<Calendar> calendars, String athleteUsername){

        Set<Calendar> filteredCalendars = calendars.stream().
                filter(calendar -> calendar.getAthlete().getUser().getUsername().equals(athleteUsername)).collect(Collectors.toSet());

        if(filteredCalendars.isEmpty()){
            return null;
        }

        Stream<CalendarDto> calendarDtoStream = filteredCalendars.stream().map(calendar -> {
            Long id = calendar.getId();
            String title = calendar.getTitle();
            Set<TrainingDayDto> trainingDayDtos = trainingDayDtos(calendar.getTrainingDays());
            return new CalendarDto(id, title, trainingDayDtos);
        });
        return calendarDtoStream.collect(Collectors.toSet());
    }
}
