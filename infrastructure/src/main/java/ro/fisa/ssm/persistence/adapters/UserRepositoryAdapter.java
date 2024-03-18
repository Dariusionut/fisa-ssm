package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.persistence.user.mapper.UserEntityMapper;
import ro.fisa.ssm.port.secondary.UserRepository;

import java.util.Collection;

/**
 * Created at 3/18/2024 by Darius
 **/
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Collection<Employee> fetchEmployees() {
        return this.jpaUserRepository.fetchEmployees()
                .stream()
                .map(UserEntityMapper.INSTANCE::toEmployeeModel).toList();
    }
}
