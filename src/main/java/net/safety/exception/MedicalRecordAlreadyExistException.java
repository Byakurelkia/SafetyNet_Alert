package net.safety.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MedicalRecordAlreadyExistException extends RuntimeException{

    public MedicalRecordAlreadyExistException(String msg){
        super(msg);
    }
}
