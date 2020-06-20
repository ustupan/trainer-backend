package edu.bachelor.trainer.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TrainingDayNotExistException extends RuntimeException {
    public TrainingDayNotExistException() {
    }

    public TrainingDayNotExistException(String message) {
        super(message);
    }
}
