package ro.fisa.ssm.security.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ro.fisa.ssm.security.AppSecurityProperties;

/**
 * Created at 4/6/2024 by Darius
 **/
public class AppLogouthandler implements LogoutHandler {
    public static final AntPathRequestMatcher logoutMatcher = new AntPathRequestMatcher("/api/v1/authentication/logout");

    private static AppSecurityProperties.JwtProperties.Cookie cookieProperties;

    public static void setCookieProperties(AppSecurityProperties.JwtProperties.Cookie cookieProperties) {
        AppLogouthandler.cookieProperties = cookieProperties;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.setClearAuthentication(true);
        logoutHandler.setInvalidateHttpSession(true);
        final Cookie cookie = new Cookie(cookieProperties.getName(), null);
        cookie.setPath(cookieProperties.getPath());
        cookie.setHttpOnly(cookieProperties.isHttpOnly());
        cookie.setSecure(cookieProperties.isSecure());
        cookie.setMaxAge(0);
        cookie.setDomain(cookieProperties.getClientDomain());
        cookie.setAttribute("sameSite", cookieProperties.getSameSite());
        cookie.setAttribute("priority", cookieProperties.getPriority());
        response.addCookie(cookie);
        logoutHandler.logout(request, response, authentication);
    }


}
