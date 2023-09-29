package net.safety.rest_Controller;

import net.safety.exception.AddressNotFoundException;
import net.safety.exception.FireStationNotFoundException;
import net.safety.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<?> personNotFoundException(PersonNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FireStationNotFoundException.class)
    public ResponseEntity<?> fireStationNotFoundExceptionHandler(FireStationNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> addressNotFoundExceptionHandler(AddressNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
