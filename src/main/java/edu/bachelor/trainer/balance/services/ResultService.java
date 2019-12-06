package edu.bachelor.trainer.balance.services;

import edu.bachelor.trainer.balance.controllers.dtos.ResultDto;
import edu.bachelor.trainer.repository.entities.Result;

import java.util.Optional;
import java.util.Set;

public interface ResultService {
    Result createResult(ResultDto resultDto, String jwtToken);

    Set<ResultDto> getAllResultsByAthleteId(Long athleteId,String jwtToken);
}
