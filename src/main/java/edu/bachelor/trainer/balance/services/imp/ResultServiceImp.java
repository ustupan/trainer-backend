package edu.bachelor.trainer.balance.services.imp;

import edu.bachelor.trainer.balance.controllers.dtos.ResultDto;
import edu.bachelor.trainer.balance.services.ResultService;
import edu.bachelor.trainer.common.exceptions.TrainerNotExistException;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.ResultRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Result;
import edu.bachelor.trainer.repository.entities.Trainer;
import edu.bachelor.trainer.security.JwtClaims;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResultServiceImp implements ResultService {

    private final AthleteRepository athleteRepository;
    private final ResultRepository resultRepository;
    private final TrainerRepository trainerRepository;


    ResultServiceImp(AthleteRepository athleteRepository,
                       ResultRepository resultRepository,
                     TrainerRepository trainerRepository){
        this.athleteRepository = athleteRepository;
        this.resultRepository = resultRepository;
        this.trainerRepository = trainerRepository;
    }


    @Override
    public Result createResult(ResultDto resultDto, String jwtToken) {

        JwtClaims jwtClaims = new JwtClaims(jwtToken);

        final Optional<Athlete> findAthlete = athleteRepository.getAthleteByUser_Username(jwtClaims.getUserUsername());

        if (!findAthlete.isPresent()) {
            throw new UsernameNotFoundException("No such athlete!");
        }

        Athlete athlete = findAthlete.get();


        Result result = new Result();
        result.setAthlete(athlete);
        result.setDescription(resultDto.getDescription());
        result.setDiscipline(resultDto.getDiscipline());
        result.setResultDate(resultDto.getResultDate());
        result.setValue(resultDto.getValue());

        return resultRepository.save(result);
    }

    @Override
    public Set<ResultDto> getAllResultsByAthleteId(Long athleteId, String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);

        final Optional<Athlete> findAthlete = athleteRepository.getAthleteById(athleteId);
        if (!findAthlete.isPresent()) {
            throw new UsernameNotFoundException("No such athlete!");
        }
        Athlete athlete = findAthlete.get();

        if(!athlete.getId().equals(athleteId) && jwtClaims.getUserRoles().contains("ROLE_ATHLETE")){
            return new HashSet<ResultDto>();
        }
        if(jwtClaims.getUserRoles().contains("ROLE_TRAINER")){
            Optional<Trainer> findTrainer = trainerRepository.getTrainerByUser_Username(jwtClaims.getUserUsername());
            if (!findTrainer.isPresent()){
                throw new TrainerNotExistException("No such trainer!");
            }
            Trainer trainer = findTrainer.get();
            if(trainer.getAthletes().stream().filter(filterAthlete -> filterAthlete.getId().equals(athleteId)).collect(Collectors.toSet()).isEmpty()){
                return new HashSet<ResultDto>();
            }
        }
        Optional<Set<Result>> findResults = resultRepository.getAllByAthleteId(athleteId);

        if(!findResults.isPresent()) return new HashSet<ResultDto>();
        Set<Result> results = new HashSet<>(findResults.get());
        return resultDtoMapper(results);
    }

    private Set<ResultDto> resultDtoMapper(Set<Result> results){

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
}
