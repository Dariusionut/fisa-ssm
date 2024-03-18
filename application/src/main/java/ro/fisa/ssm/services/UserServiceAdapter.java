package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.port.primary.UserService;
import ro.fisa.ssm.port.secondary.UserRepository;

import java.util.Collection;

/**
 * Created at 3/18/2024 by Darius
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceAdapter implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> getEmployees() {
        return this.userRepository.fetchEmployees();
    }
}
