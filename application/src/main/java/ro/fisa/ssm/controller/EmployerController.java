package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.port.primary.EmployerService;
import ro.fisa.ssm.structures.DomainPage;

/**
 * Created at 4/4/2024 by Darius
 **/
//@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/employer")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EmployerController {

    private final EmployerService employerService;

    @GetMapping
    public ResponseEntity<DomainPage<Employer>> getEmployers(@RequestParam(value = "pageNumber", defaultValue = "0") int number,
                                                             @RequestParam(value = "pageSize", defaultValue = "50") int size) {

        final var page = this.employerService.getEmployers(number, size);
        return ResponseEntity.ok(page);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(path = "/{employerName}")
    public ResponseEntity<Employer> getByName(@PathVariable(name = "employerName") final String employerName) {
        final Employer employer = this.employerService.getByName(employerName);
        return ResponseEntity.ok(employer);
    }
}
