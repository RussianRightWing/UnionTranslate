package rightwing.ut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rightwing.ut.exception.TranslatorsServiceUnavailableException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(TranslatorsServiceUnavailableException.class)
    public ResponseEntity<String> handleException(TranslatorsServiceUnavailableException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}