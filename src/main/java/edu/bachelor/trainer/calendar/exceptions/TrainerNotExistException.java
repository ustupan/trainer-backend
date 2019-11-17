package edu.bachelor.trainer.calendar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TrainerNotExistException extends RuntimeException{
    public TrainerNotExistException() {
    }

    public TrainerNotExistException(String message) {
        super(message);
    }
}



