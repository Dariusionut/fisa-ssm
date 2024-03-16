package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.persistence.user.mapper.UserEntityMapper;
import ro.fisa.ssm.port.secondary.UserRepository;

/**
 * Created at 3/10/2024 by Darius
 **/

@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    private Long saveEmployee(Employee employee) {
        final UserEntity userEntity = UserEntityMapper.INSTANCE.toEntity(employee);
        final UserEntity savedEntity = this.jpaUserRepository.save(userEntity);
        return savedEntity.getId();
    }
}
