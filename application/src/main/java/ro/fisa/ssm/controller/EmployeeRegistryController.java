package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.fisa.ssm.controller.mapper.MultipartFileMapper;
import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.port.primary.EmployeeRegistryService;

import java.io.IOException;
import java.util.Collection;

/**
 * Created at 3/14/2024 by Darius
 **/

@RestController
@RequestMapping(path = "api/v1/employee-registry")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EmployeeRegistryController {

    private final EmployeeRegistryService employeeRegistryService;

    @PostMapping
    public ResponseEntity<Collection<Contract>> uploadEmployeeRegistry(@RequestPart(name = "file") final MultipartFile file,
                                                                       @RequestPart(name = "induction", required = false) final String induction
    ) throws IOException {
        final AppDocument document = MultipartFileMapper.INSTANCE.toAppDocument(file);
        return ResponseEntity.ok(this.employeeRegistryService.saveEmployeesFromRegistry(document, induction));
    }
}
