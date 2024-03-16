package ro.fisa.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created at 3/9/2024 by Darius
 **/

@Controller
public class AuthController {

    @GetMapping(path = "/", produces = "text/html")
    public String getIndexView(final Model model) {

        return "index";
    }

    @GetMapping(path = "/login", produces = "text/html")
    public String getLoginView(final Model model) {

        return "login";
    }
}
