package edu.bachelor.trainer.common.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TrainerAlreadyHasAthleteException extends RuntimeException {
    public TrainerAlreadyHasAthleteException() {
    }

    public TrainerAlreadyHasAthleteException(String message) {
        super(message);
    }
}

