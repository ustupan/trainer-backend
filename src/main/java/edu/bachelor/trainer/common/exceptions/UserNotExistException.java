package edu.bachelor.trainer.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
    }

    public UserNotExistException(String message) {
        super(message);
    }
}
