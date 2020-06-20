package edu.bachelor.trainer.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CalendarNotExistException extends RuntimeException {

    public CalendarNotExistException() {
    }

    public CalendarNotExistException(String message) {
        super(message);
    }
}
