package edu.bachelor.trainer.security.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserNameExistsException extends RuntimeException {

    public UserNameExistsException() {
    }

    public UserNameExistsException(String s) {
    }

}
