package edu.bachelor.trainer.security.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UndefinedRoleException extends RuntimeException {

    public UndefinedRoleException() {
    }

    public UndefinedRoleException(String s) {
    }

}

