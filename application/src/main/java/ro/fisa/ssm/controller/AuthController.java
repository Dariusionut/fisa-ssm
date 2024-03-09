package ro.fisa.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created at 3/9/2024 by Darius
 **/

@Controller
public class AuthController {

    @GetMapping(path = "/login", produces = "text/html")
    public String getLoginView(){

        return "login";
    }
}
