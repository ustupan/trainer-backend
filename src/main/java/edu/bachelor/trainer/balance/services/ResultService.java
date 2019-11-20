package edu.bachelor.trainer.balance.services;

import edu.bachelor.trainer.balance.controllers.dtos.ResultDto;
import edu.bachelor.trainer.repository.entities.Result;

public interface ResultService {
    Result createResult(ResultDto resultDto, String jwtToken);
}
