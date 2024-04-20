package ro.fisa.ssm.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import ro.fisa.ssm.security.AppUserDetails;

/**
 * Created at 4/9/2024 by Darius
 **/
@Slf4j
public class AppAccessDeniedHandler implements AccessDeniedHandler {
    private static final int SB_CAPACITY = 8;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        final String uri = request.getRequestURI();
        final String method = request.getMethod();
        final StringBuilder sb = new StringBuilder(SB_CAPACITY);
        sb.append(accessDeniedException.getMessage()).append(" = ");

        if (authentication == null) {
            sb.append("Unknown user");
        } else if ((authentication instanceof AnonymousAuthenticationToken)) {
            sb.append(authentication.getName());
        } else {
            final AppUserDetails details = (AppUserDetails) authentication.getPrincipal();
            sb.append(details.getFullName());
        }
        sb.append(" is trying to access protected resource: ").append(method).append(": ").append(uri);
        log.error(sb.toString());
    }
}
