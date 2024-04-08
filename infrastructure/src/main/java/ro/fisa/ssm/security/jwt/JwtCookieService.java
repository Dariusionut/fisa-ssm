package ro.fisa.ssm.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.security.AppSecurityProperties;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created at 4/6/2024 by Darius
 **/
@Service
@RequiredArgsConstructor
public final class JwtCookieService {

    private final JwtService jwtService;
    private final AppSecurityProperties.JwtProperties.Cookie cookieProperties;

    public Cookie generateJwtCookie(final UserDetails userDetails) {
        final String jwt = this.jwtService.generateToken(userDetails);
        final Cookie cookie = new Cookie(cookieProperties.getName(), jwt);
        cookie.setDomain(cookieProperties.getClientDomain());
        cookie.setSecure(cookieProperties.isSecure());
        cookie.setHttpOnly(cookieProperties.isHttpOnly());
        cookie.setPath(cookieProperties.getPath());
        cookie.setMaxAge(cookieProperties.getMaxAge());
        cookie.setAttribute("sameSite", cookieProperties.getSameSite());
        cookie.setAttribute("priority", cookieProperties.getPriority());
        return cookie;
    }

    public Cookie getJwtCookie(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();

        return cookies == null ? null : Arrays.stream(cookies)
                .filter(c -> c.getName().equals(this.cookieProperties.getName()))
                .findFirst()
                .orElse(null);
    }

    public String getJwt(final HttpServletRequest request) {
        final Cookie jwtCookie = this.getJwtCookie(request);
        return Optional.ofNullable(jwtCookie).map(Cookie::getValue).orElse(null);
    }


}
