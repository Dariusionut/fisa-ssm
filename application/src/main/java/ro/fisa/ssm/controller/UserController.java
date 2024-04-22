package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.model.InductionDetail;
import ro.fisa.ssm.port.primary.UserService;
import ro.fisa.ssm.structures.DomainPage;

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

    @GetMapping(path = "/induction/unaccepted")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<DomainPage<InductionDetail>> getUnacceptedInductions(@RequestParam(value = "pageNumber", defaultValue = "0") final int pageNumber,
                                                                               @RequestParam(value = "pageSize", defaultValue = "50") final int pageSize
    ) {
        final DomainPage<InductionDetail> inductionDetails = this.userService.fetchUnacceptedInductions(pageNumber, pageSize);
        return ResponseEntity.ok(inductionDetails);
    }

    @PutMapping(path = "/induction/unaccepted")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Void> acceptInduction(@RequestParam("contractId") final long contractId) {
        this.userService.acceptInduction(contractId);
        return ResponseEntity.noContent().build();
    }

}
