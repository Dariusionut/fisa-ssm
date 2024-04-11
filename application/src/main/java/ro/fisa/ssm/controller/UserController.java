package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.port.primary.UserService;

import java.util.Collection;

/**
 * Created at 3/18/2024 by Darius
 **/
@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Collection<Employee>> getEmployees() {
        return ResponseEntity.ok(this.userService.getEmployees());
    }
}
