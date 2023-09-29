package net.safety.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FireStationIsAlreadyExistException extends RuntimeException {

    public FireStationIsAlreadyExistException(String s) {
        super(s);
    }
}
