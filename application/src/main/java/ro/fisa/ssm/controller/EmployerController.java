package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class EmployerController {

    private final EmployerService employerService;

    @GetMapping
    public ResponseEntity<DomainPage<Employer>> getEmployers(@RequestParam(value = "pageNumber", defaultValue = "0") int number,
                                                             @RequestParam(value = "pageSize", defaultValue = "50") int size) {

        final var page = this.employerService.getEmployers(number, size);
        return ResponseEntity.ok(page);
    }
}
