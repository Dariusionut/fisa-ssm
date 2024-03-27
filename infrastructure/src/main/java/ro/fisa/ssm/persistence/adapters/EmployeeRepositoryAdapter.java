package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.persistence.user.mapper.UserEntityMapper;
import ro.fisa.ssm.port.secondary.EmployeeRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created at 3/10/2024 by Darius
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<Employee> findByCnp(String cnp) {
        return this.jpaUserRepository.findByCnp(cnp)
                .map(UserEntityMapper.INSTANCE::toEmployeeModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAllByCnp(Collection<String> cnpList) {
        return this.jpaUserRepository.fetchAllByCnp(cnpList)
                .parallel()
                .map(UserEntityMapper.INSTANCE::toEmployeeIgnoreContracts)
                .toList();
    }


    public Employee save(Employee employee) {
        return null;
    }

}
