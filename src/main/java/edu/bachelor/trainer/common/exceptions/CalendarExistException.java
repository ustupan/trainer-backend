package edu.bachelor.trainer.common.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class CalendarExistException extends RuntimeException {
    public CalendarExistException() {
    }

    public CalendarExistException(String message) {
        super(message);
    }
}
