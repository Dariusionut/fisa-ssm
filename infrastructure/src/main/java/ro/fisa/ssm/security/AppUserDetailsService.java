package ro.fisa.ssm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.exceptions.AuthenticationException;
import ro.fisa.ssm.persistence.user.JpaUserRepository;

/**
 * Created at 3/9/2024 by Darius
 **/

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.jpaUserRepository.findByCnpOrEmail(username)
                .map(AppUserDetails::new)
                .orElseThrow(AuthenticationException::new);
    }
}
