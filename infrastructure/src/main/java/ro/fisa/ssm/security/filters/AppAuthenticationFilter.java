package ro.fisa.ssm.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.MimeTypeUtils;
import ro.fisa.ssm.exceptions.AppRuntimeException;
import ro.fisa.ssm.security.AppUserDetails;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;

import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;

/**
 * Created at 3/9/2024 by Darius
 **/
@Slf4j
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String ENDPOINT = "/api/v1/authentication/login";
    private static final AntPathRequestMatcher REQUEST_MATCHER = new AntPathRequestMatcher(ENDPOINT, POST.name());

    private final ObjectMapper objectMapper;

    public AppAuthenticationFilter(final AuthenticationManager authenticationManager,
                                   final ObjectMapper objectMapper) {
        super(authenticationManager);
        super.setRequiresAuthenticationRequestMatcher(REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final LoginRequest loginRequest;
        try {
            final InputStream is = request.getInputStream();
            loginRequest = this.objectMapper.readValue(is, LoginRequest.class);
        } catch (Exception e) {
            throw new AppRuntimeException(e);
        }
        final var auth = new UsernamePasswordAuthenticationToken(loginRequest, null);
        return super.getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Authentication successfully");
        final Object principal = authResult.getPrincipal();
        final AppUserDetails userDetails = (AppUserDetails) principal;
        final Cookie cookie = new Cookie("JwtCookie", userDetails.getJwt());
        response.addCookie(cookie);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);

        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.warn("Authentication failed");
        super.unsuccessfulAuthentication(request, response, failed);
    }


}
