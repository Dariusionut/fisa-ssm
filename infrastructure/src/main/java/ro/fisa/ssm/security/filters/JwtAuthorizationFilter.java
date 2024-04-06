package ro.fisa.ssm.security.filters;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.fisa.ssm.security.jwt.JwtCookieService;
import ro.fisa.ssm.security.jwt.JwtService;

import java.io.IOException;

/**
 * Created at 4/6/2024 by Darius
 **/
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtCookieService cookieService;

    public JwtAuthorizationFilter(final JwtCookieService cookieService) {
        this.cookieService = cookieService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        final String uri = request.getRequestURI();
        return uri.endsWith("/login") || uri.endsWith("/logout");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String jwt = this.cookieService.getJwt(request);
        try {
            this.authorize(jwt);
            filterChain.doFilter(request, response);
        } catch (AuthorizationServiceException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("");
        }
    }

    private void authorize(final String jwt) {
        if (jwt == null) {
            throw new AuthorizationServiceException("JWT token is missing");
        }
        final UserDetails details;
        try {
            details = JwtService.extractUserDetails(jwt);
        } catch (SignatureException e) {
            throw new AuthorizationServiceException("Invalid JWT");
        } catch (ExpiredJwtException e) {
            throw new AuthorizationServiceException("Expired jwt");
        } catch (Exception e) {
            throw new AuthorizationServiceException("Unexpected exception occurred trying to parse JWT");
        }

        final var auth = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        final SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
    }


}
