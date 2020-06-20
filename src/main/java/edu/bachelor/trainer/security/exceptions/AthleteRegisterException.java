package edu.bachelor.trainer.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AthleteRegisterException extends RuntimeException {


    public AthleteRegisterException() {
    }

    public AthleteRegisterException(String message) {
        super(message);
    }
}
