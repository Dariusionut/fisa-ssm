package ro.fisa.ssm.security;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ro.fisa.ssm.security.handler.AppLogouthandler;

import java.util.List;

/**
 * Created at 4/9/2024 by Darius
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppSecurityProperties {
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "spring.security.user")
    public static class UserProperties {
        private String password;
    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "spring.security.cors")
    public static class CorsProperties {
        private static final String REGISTER_PATTERN = "/**";
        private String origin;
        private boolean allowCredentials;
        private long maxAge;
        private List<String> allowedMethods;
        private List<String> allowedHeaders;
        private List<String> exposeHeaders;


        public CorsConfigurationSource getCorsConfigurationSource() {
            final CorsConfiguration corsConfiguration = this.getCorsConfiguration();
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration(REGISTER_PATTERN, corsConfiguration);
            return source;
        }

        private CorsConfiguration getCorsConfiguration() {
            final CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowCredentials(this.isAllowCredentials());
            corsConfiguration.setAllowedOrigins(List.of(this.getOrigin()));
            corsConfiguration.setAllowedMethods(this.getAllowedMethods());
            corsConfiguration.setAllowedHeaders(this.getAllowedHeaders());
            corsConfiguration.setExposedHeaders(this.getExposeHeaders());
            corsConfiguration.setMaxAge(this.getMaxAge());
            return corsConfiguration;
        }


    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "spring.security.jwt")
    public static class JwtProperties {

        private long expiration;

        @Getter
        @Setter
        @Component
        @ConfigurationProperties(prefix = "spring.security.jwt.cookie")
        public static class Cookie {
            private String clientDomain;
            private String name;
            private String path;
            private String sameSite;
            private String priority;
            private int maxAge;
            private boolean secure;
            private boolean httpOnly;

            @PostConstruct
            private void postConstruct() {
                AppLogouthandler.setCookieProperties(this);
            }
        }
    }
}
