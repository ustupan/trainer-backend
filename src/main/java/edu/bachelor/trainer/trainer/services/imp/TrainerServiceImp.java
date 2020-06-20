package edu.bachelor.trainer.trainer.services.imp;

import edu.bachelor.trainer.common.exceptions.AthleteNotExistException;
import edu.bachelor.trainer.common.exceptions.TrainerAlreadyHasAthleteException;
import edu.bachelor.trainer.common.exceptions.TrainerNotExistException;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.*;
import edu.bachelor.trainer.security.JwtClaims;
import edu.bachelor.trainer.trainer.controllers.dtos.*;
import edu.bachelor.trainer.trainer.services.TrainerService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class TrainerServiceImp implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final AthleteRepository athleteRepository;

    public TrainerServiceImp(TrainerRepository trainerRepository, AthleteRepository athleteRepository) {
        this.trainerRepository = trainerRepository;
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Trainer addAthlete(String athleteUsername, String jwtToken) {

        JwtClaims jwtClaims = new JwtClaims(jwtToken);
        Optional<Athlete> findAthlete = athleteRepository.getAthleteByUser_Username(athleteUsername);

        if (!findAthlete.isPresent()) {
            throw new AthleteNotExistException("No such athlete!");
        }

        Trainer trainer = getTrainerByUserUsername(jwtClaims.getUserUsername());
        Set<Athlete> athletes = trainer.getAthletes();
        Set<Athlete> filtered = athletes.stream().
                filter(athlete -> athlete.getUser().getUsername().equals(athleteUsername)).collect(Collectors.toSet());
        if(!filtered.isEmpty()) {
            throw new TrainerAlreadyHasAthleteException("Trainer already has athlete!");
        }

        trainer.addAthlete(findAthlete.get());

        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer getTrainerByUserUsername(String username) {
        Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(username);
        if (!findTrainer.isPresent()) {
            throw new TrainerNotExistException("No such trainer!");
        }
        return findTrainer.get();
    }

    @Override
    public Set<AthleteDto> getAllTrainerAthletes(String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);
        final Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(jwtClaims.getUserUsername());
        if (!findTrainer.isPresent()) {
            throw new TrainerNotExistException("No such trainer!");
        }
        Trainer trainer = findTrainer.get();

        Set<Athlete> athletes = findTrainer.get().getAthletes();

        TrainerDto trainerDto = new TrainerDto();

        return athleteDtoMapper(trainer.getAthletes(), findTrainer.get().getUser().getUsername());
    }

    private Set<AthleteDto> athleteDtoMapper(Set<Athlete> athletes, String trainerUsername){
        Set<AthleteDto> athleteDtos = new HashSet<AthleteDto>();

        Stream<AthleteDto> athleteDtoStream = athletes.stream().map(athlete -> {
            Long id = athlete.getId();
            String username = athlete.getUser().getUsername();
            AthleteDto athleteDto = new AthleteDto();
            athleteDto.setId(id);
            athleteDto.setUserName(username);
            athleteDto.setGender(athlete.getUser().getGender());
            athleteDto.setEmail(athlete.getUser().getEmail());
            //athleteDto.setResultsIds(athlete.getResults().stream().map(Result::getId).collect(Collectors.toSet()));
            //athleteDto.setCalendarId(getAthleteCalendarId(athlete.getCalendars(), trainerUsername));
            return athleteDto;
        });
        return athleteDtoStream.collect(Collectors.toSet());
    }

//    private Set<ResultDto> resultDtoMapper(Set<Result> results){
//        Set<ResultDto> resultDtos = new HashSet<ResultDto>();
//
//        Stream<ResultDto> resultDtoStream = results.stream().map(result -> {
//            Long id = result.getId();
//            String discipline = result.getDiscipline();
//            String description = result.getDescription();
//            Date resultDate = result.getResultDate();
//            Long value = result.getValue();
//            return new ResultDto(id,discipline,description,resultDate,value);
//        });
//
//        return resultDtoStream.collect(Collectors.toSet());
//    }
//
//
//    private Long getAthleteCalendarId(Set<Calendar> calendars, String trainerUsername) {
//        Set<Calendar> filteredCalendars = calendars.stream().
//                filter(calendar -> calendar.getTrainer().getUser().getUsername().equals(trainerUsername)).collect(Collectors.toSet());
//        Calendar calendar = filteredCalendars.iterator().next();
//        return calendar.getId();
//    }

    //
//    private Set<TrainingDayDto> trainingDayDtos(Set<TrainingDay> trainingDays){
//        Stream<TrainingDayDto> trainingDayDtoStream = trainingDays.stream().map(trainingDay -> {
//            Long id = trainingDay.getId();
//            String title = trainingDay.getTitle();
//            String description = trainingDay.getDescription();
//            Date trainingDate = trainingDay.getTrainingDate();
//            String note = trainingDay.getNote();
//            Integer motivationLevel = trainingDay.getMotivationLevel();
//            Integer dispositionLevel = trainingDay.getDispositionLevel();
//            return new TrainingDayDto(id, title, description, trainingDate, note, motivationLevel, dispositionLevel);
//        });
//        return trainingDayDtoStream.collect(Collectors.toSet());
//    }

//    private CalendarDto calendarDtoMapper(Set<Calendar> calendars, String trainerUsername){
//
//        Set<Calendar> filteredCalendars = calendars.stream().
//                filter(calendar -> calendar.getTrainer().getUser().getUsername().equals(trainerUsername)).collect(Collectors.toSet());
//
//        //todo ew sprawdzac czy nie ma wiecej niz 1 kalendarz
//
//        if(filteredCalendars.isEmpty()){
//            return null;
//        }
//        Calendar calendar = filteredCalendars.iterator().next();
//        CalendarDto calendarDto = new CalendarDto();
//        calendarDto.setId(calendar.getId());
//        calendarDto.setTitle(calendar.getTitle());
//        calendarDto.setTrainingDays(trainingDayDtos(calendar.getTrainingDays()));
//
//        return calendarDto;
//    }
}
