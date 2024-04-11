package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.port.primary.EmployerService;
import ro.fisa.ssm.port.secondary.EmployerRepository;
import ro.fisa.ssm.structures.DomainPage;

/**
 * Created at 4/4/2024 by Darius
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerServiceAdapter implements EmployerService {
    private final EmployerRepository employerRepository;

    @Override
    public DomainPage<Employer> getEmployers(int number, int size) {
        log.info("getEmployers page with params: number = {}, size = {}", number, size);
        final Pageable pageable = PageRequest.of(number, size);
        return this.employerRepository.fetchPage(pageable);
    }

    @Override
    public Employer getByName(String employerName) {
        return this.employerRepository.fetchByName(employerName.trim())
                .orElse(null);
    }
}
