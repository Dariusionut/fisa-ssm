package ro.fisa.ssm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.fisa.ssm.security.filters.JwtAuthenticationFilter;
import ro.fisa.ssm.security.filters.JwtAuthorizationFilter;
import ro.fisa.ssm.security.handler.AppLogoutSuccessHandler;
import ro.fisa.ssm.security.handler.AppLogouthandler;
import ro.fisa.ssm.security.jwt.JwtCookieService;

import static ro.fisa.ssm.enums.AppCookie.JSESSIONID;

/**
 * Created at 3/9/2024 by Darius
 **/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public LogoutHandler logoutHandler() {
        return new AppLogouthandler();
    }

    @Bean
    @Primary
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new AppLogoutSuccessHandler();
    }

    @Bean
    @Primary
    public UsernamePasswordAuthenticationFilter authenticationFilter(final AuthenticationManager authenticationManager,
                                                                     final JwtCookieService jwtCookieService,
                                                                     final ObjectMapper objectMapper) {

        return new JwtAuthenticationFilter(authenticationManager, jwtCookieService, objectMapper);
    }

    @Bean
    @Primary
    public OncePerRequestFilter authorizationFilter(final JwtCookieService cookieService) {
        return new JwtAuthorizationFilter(cookieService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(SecurityConfig::customizeSessionManagement)
                .authenticationManager(authenticationManager)
                .sessionManagement(SecurityConfig::customizeSessionManagement)
                .logout(this::customizeLogout)
                .build();
    }

    private void customizeLogout(LogoutConfigurer<HttpSecurity> logoutConf) {
        SecurityContextHolder.clearContext();
        logoutConf
                .addLogoutHandler(this.logoutHandler())
                .logoutSuccessHandler(this.logoutSuccessHandler())
                .logoutRequestMatcher(AppLogouthandler.logoutMatcher)
                .deleteCookies(JSESSIONID.value(), "JwtCookie")
                .clearAuthentication(true)
                .invalidateHttpSession(true);
    }

    private static void customizeSessionManagement(SessionManagementConfigurer<HttpSecurity> config) {
        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
