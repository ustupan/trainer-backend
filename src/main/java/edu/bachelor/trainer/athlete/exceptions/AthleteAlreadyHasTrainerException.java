package edu.bachelor.trainer.athlete.exceptions;

public class AthleteAlreadyHasTrainerException extends RuntimeException{
    public AthleteAlreadyHasTrainerException() {
    }

    public AthleteAlreadyHasTrainerException(String message) {
        super(message);
    }
}
