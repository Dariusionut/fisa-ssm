package ro.fisa.ssm.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;

/**
 * Created at 3/9/2024 by Darius
 **/
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String ENDPOINT = "/login";
    private static final AntPathRequestMatcher REQUEST_MATCHER = new AntPathRequestMatcher(ENDPOINT, POST.name());

    public AppAuthenticationFilter(final AuthenticationManager authenticationManager){
        super(authenticationManager);
        super.setRequiresAuthenticationRequestMatcher(REQUEST_MATCHER);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
