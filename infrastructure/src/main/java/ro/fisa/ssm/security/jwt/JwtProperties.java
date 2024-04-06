package ro.fisa.ssm.security.jwt;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created at 4/6/2024 by Darius
 **/
@Getter
@Component
public class JwtProperties {

    private @Value("${jwt.expiration}") long expiration;

    @Getter
    @Component
    public static class Cookie {
        private @Value("${jwt.cookie.name}") String name;
        private @Value("${jwt.cookie.secure}") boolean secure;
        private @Value("${jwt.cookie.httpOnly}") boolean httpOnly;
        private @Value("${jwt.cookie.path}") String path;
        private @Value("${jwt.cookie.sameSite}") String sameSite;
        private @Value("${jwt.cookie.priority}") String priority;
        private @Value("${jwt.cookie.maxAge}") int maxAge;


    }

}
