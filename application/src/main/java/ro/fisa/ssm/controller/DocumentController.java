package ro.fisa.ssm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.fisa.ssm.controller.mapper.MultipartFileMapper;
import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.port.primary.DocumentService;

import java.io.IOException;
import java.util.List;

/**
 * Created at 3/14/2024 by Darius
 **/

@RestController
@RequestMapping(path = "api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(path = "/employee-registry")
    public ResponseEntity<List<Employee>> uploadEmployeeRegistry(@RequestPart(name = "file") MultipartFile file) throws IOException {
        final AppDocument document = MultipartFileMapper.INSTANCE.toAppDocument(file);
        final List<Employee> response = this.documentService.manageEmployeeRegistry(document);
        return ResponseEntity.ok(response);
    }
}
