package ro.fisa.ssm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 3/9/2024 by Darius
 **/
@Component
@RequiredArgsConstructor
public class AppAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailsService;


    @Override
    @Transactional(readOnly = true)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        final var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return auth;
    }
}
