package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.port.secondary.UserRepository;

/**
 * Created at 3/10/2024 by Darius
 **/

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
}
