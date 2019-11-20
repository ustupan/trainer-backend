package edu.bachelor.trainer.balance.services.imp;

import edu.bachelor.trainer.balance.controllers.dtos.ResultDto;
import edu.bachelor.trainer.balance.services.ResultService;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.ResultRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Result;
import edu.bachelor.trainer.security.JwtClaims;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImp implements ResultService {

    private final AthleteRepository athleteRepository;
    private final ResultRepository resultRepository;


    ResultServiceImp(AthleteRepository athleteRepository,
                       ResultRepository resultRepository){
        this.athleteRepository = athleteRepository;
        this.resultRepository = resultRepository;
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
}
