package edu.bachelor.trainer.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AthleteNotExistException extends RuntimeException {
    public AthleteNotExistException() {
    }

    public AthleteNotExistException(String message) {
        super(message);
    }
}
