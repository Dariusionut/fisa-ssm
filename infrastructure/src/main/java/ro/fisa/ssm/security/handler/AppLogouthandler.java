package ro.fisa.ssm.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created at 4/6/2024 by Darius
 **/
public class AppLogouthandler implements LogoutHandler {
    public static final AntPathRequestMatcher logoutMatcher = new AntPathRequestMatcher("/api/v1/authentication/logout");

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.setClearAuthentication(true);
        logoutHandler.setInvalidateHttpSession(true);
        logoutHandler.logout(request, response, authentication);
    }
}
