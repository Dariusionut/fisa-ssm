package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.port.primary.UserService;
import ro.fisa.ssm.port.secondary.UserRepository;

/**
 * Created at 3/10/2024 by Darius
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceAdapter implements UserService {

    private final UserRepository userRepository;

//    private final AppAuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void createAccount() {

    }

    @Override
    public void auth() {
//        this.authenticationManager.authenticate(auth);
        throw new IllegalStateException();

    }
}
