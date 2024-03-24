package ro.fisa.ssm.controller.advices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.fisa.ssm.controller.response.ErrorResponse;
import ro.fisa.ssm.exceptions.NotFoundException;

/**
 * Created at 3/9/2024 by Darius
 **/
@Slf4j
@RestControllerAdvice
public class ErrorControllerAdvice {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> exception(final NotFoundException e) {
        log.error("Not found exception = {}", e.getMessage());
        final ErrorResponse response = new ErrorNotFoundResponse(e);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
