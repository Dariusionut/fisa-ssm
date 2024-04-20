package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.model.InductionDetail;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Collection<Employee>> getEmployees() {
        return ResponseEntity.ok(this.userService.getEmployees());
    }

    @GetMapping(path = "/unaccepted-inductions")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Collection<InductionDetail>> getUnacceptedInductions(@RequestParam("employeeId") final long employeeId) {
        final Collection<InductionDetail> inductionDetails = this.userService.fetchUnacceptedInductions(employeeId);
        return ResponseEntity.ok(inductionDetails);
    }

    @PutMapping(path = "/unaccepted-inductions")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Void> acceptInduction(@RequestParam("contractId") final long contractId) {
        this.userService.acceptInduction(contractId);
        return ResponseEntity.noContent().build();
    }

}
