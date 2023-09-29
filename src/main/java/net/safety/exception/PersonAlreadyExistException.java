package net.safety.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonAlreadyExistException extends RuntimeException{

    public PersonAlreadyExistException(String msg){
        super(msg);
    }

}
