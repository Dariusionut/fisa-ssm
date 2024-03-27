package ro.fisa.ssm.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ro.fisa.ssm.security.filters.AppAuthenticationFilter;

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

//    @Bean
//    @Primary
    public UsernamePasswordAuthenticationFilter authenticationFilter(final AuthenticationManager authenticationManager){

        return new AppAuthenticationFilter(authenticationManager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(SecurityConfig::customizeSessionManagement)
                .authenticationManager(authenticationManager)
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/api/v1/login")
//                        .successForwardUrl("/")
                        .failureForwardUrl("/login?error=true")
                )
//                .logout(SecurityConfig::customizeLogout)
                .build();
    }

    private static void customizeLogout(LogoutConfigurer<HttpSecurity> logoutConf) {
        SecurityContextHolder.clearContext();
        logoutConf
//                .addLogoutHandler(PortalLogoutHandler.getInstance())
                .addLogoutHandler((request, response, authentication) -> {
                    try {
                        request.logout();
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                })
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout"))
                .logoutUrl("/api/v1/logout")

                .deleteCookies(JSESSIONID.value())
                .invalidateHttpSession(true);
    }

    private static void customizeSessionManagement(SessionManagementConfigurer<HttpSecurity> config) {
        config.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true);
    }

}
