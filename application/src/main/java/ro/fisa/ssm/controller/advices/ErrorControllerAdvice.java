package ro.fisa.ssm.controller.advices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.fisa.ssm.exceptions.AuthenticationException;

/**
 * Created at 3/9/2024 by Darius
 **/
@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String exception(final AuthenticationException e, final Model model){
        log.error("Error during authentication", e);
        final String msg = e.getMessage();
        model.addAttribute("errorMessage", msg);
        return "login";
    }

}
