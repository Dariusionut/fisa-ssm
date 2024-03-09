package ro.fisa.ssm.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.exceptions.AuthenticationException;
import ro.fisa.ssm.persistence.role.mapper.RoleEntityMapper;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.utils.AppRoles;

import java.util.List;

/**
 * Created at 3/9/2024 by Darius
 **/

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final UserEntity ADMIN = new UserEntity(1L, "123", "pass", RoleEntityMapper.INSTANCE.toEntity(AppRoles.ROLE_ADMIN));

    final List<UserEntity> temporaryUsers = List.of(ADMIN);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return temporaryUsers.stream()
                .filter(u -> u.getCnp().equals(username))
                .map(AppUserDetails::new)
                .findAny().orElseThrow(AuthenticationException::new);
    }
}
