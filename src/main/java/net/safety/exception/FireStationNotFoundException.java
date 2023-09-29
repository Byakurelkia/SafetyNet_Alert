package net.safety.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FireStationNotFoundException extends RuntimeException{

    private final static Logger logger = LoggerFactory.getLogger(FireStationNotFoundException.class);

    public FireStationNotFoundException(String message) {
        super(message);
    }

    public FireStationNotFoundException() {

    }
}
