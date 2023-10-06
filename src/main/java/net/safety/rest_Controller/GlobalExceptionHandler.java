package net.safety.rest_Controller;

import net.safety.exception.*;
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

    @ExceptionHandler(FireStationIsAlreadyExistException.class)
    public ResponseEntity<?> fireStationIsAlreadyExistExceptionHandler(FireStationIsAlreadyExistException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataLoadErrorException.class)
    public ResponseEntity<?> errorDataLoadingFromFileExceptionHandler(DataLoadErrorException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PersonAlreadyExistException.class)
    public ResponseEntity<?> personIsAlreadyExistExceptionHandler(PersonAlreadyExistException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicalRecordNotFoundException.class)
    public ResponseEntity<?> MedicalRecordNotFoundExceptionHandler(MedicalRecordNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicalRecordAlreadyExistException.class)
    public ResponseEntity<?> MedicalRecordIsAlreadyExistExceptionHandler(MedicalRecordAlreadyExistException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
