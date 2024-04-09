package ro.fisa.ssm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.security.exceptions.NoActiveContractException;
import ro.fisa.ssm.security.filters.LoginRequest;

/**
 * Created at 3/9/2024 by Darius
 **/
@Component
@RequiredArgsConstructor
public class AppAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final LoginRequest loginRequest = (LoginRequest) authentication.getPrincipal();
        final String username = loginRequest.username();
        final String rawPassword = loginRequest.password();

        final AppUserDetails userDetails = (AppUserDetails) this.userDetailsService.loadUserByUsername(username);

        if (!userDetails.hasAnyActiveContract()) {
            throw new NoActiveContractException();
        }
        final boolean passwordValid = this.passwordEncoder.matches(rawPassword, userDetails.getPassword());
        if (!passwordValid) {
            throw new BadCredentialsException("Invalid username, password or account might be disabled");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
