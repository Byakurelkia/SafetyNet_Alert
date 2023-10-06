package net.safety.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataLoadErrorException extends RuntimeException{

    public DataLoadErrorException(String msg){
        super(msg);
    }
}
