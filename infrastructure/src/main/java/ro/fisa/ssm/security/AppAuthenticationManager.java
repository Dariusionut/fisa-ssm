package ro.fisa.ssm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.persistence.role.mapper.RoleEntityMapper;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.utils.AppRoles;

import java.util.List;

/**
 * Created at 3/9/2024 by Darius
 **/
@Component
@RequiredArgsConstructor
public class AppAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailsService;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
