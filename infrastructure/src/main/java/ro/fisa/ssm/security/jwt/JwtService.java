package ro.fisa.ssm.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.persistence.user.projection.UserSecurityDetailProjection;
import ro.fisa.ssm.security.AppSecurityProperties;
import ro.fisa.ssm.security.AppUserDetails;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * Created at 4/6/2024 by Darius
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private static final SecretKey SECRET_KEY = JwtKeyHolder.getSecretKey();

    private final AppSecurityProperties.JwtProperties jwtProperties;

    public String generateToken(final UserDetails userDetails) {
        final AppUserDetails details = (AppUserDetails) userDetails;
        final Instant now = Instant.now();
        final Instant expiryDate = now.plusSeconds(jwtProperties.getExpiration());
        return Jwts.builder()
                .claim("sub", details.getUsername())
                .claim("iat", Date.from(now))
                .claim("exp", Date.from(expiryDate))
                .claim("id", details.getId())
                .claim("firstName", details.getFirstName())
                .claim("lastName", details.getLastname())
                .claim("nationality", details.getNationality())
                .claim("role", details.getAuthorities().get(0).getAuthority())
                .claim("isEnabled", details.isEnabled())
                .claim("isCredentialsNonExpired", details.isCredentialsNonExpired())
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims getAllClaims(final String jwt) throws SignatureException, ExpiredJwtException {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public static String extractRole(final String jwt) throws SignatureException, ExpiredJwtException {
        final Claims claims = getAllClaims(jwt);
        return claims.get("role", String.class);
    }

    public static UserDetails extractUserDetails(final String jwt) throws SignatureException, ExpiredJwtException {
        try {
            final Claims claims = getAllClaims(jwt);
            final String roleClaim = extractRole(jwt);
            final String roleWithoutPrefix = roleClaim.startsWith("ROLE_") ? roleClaim.substring(5) : roleClaim;
            final UserSecurityDetailProjection detailProjection = UserSecurityDetailProjection.builder()
                    .id(claims.get("id", Long.class))
                    .firstName(claims.get("firstName", String.class))
                    .lastName(claims.get("lastName", String.class))
                    .username(claims.getSubject())
                    .role(roleWithoutPrefix)
                    .nationality(claims.get("nationality", String.class))
                    .build();

            return new AppUserDetails(detailProjection);
        } catch (SignatureException e) {
            log.error("Invalid signature", e);
            throw e;
        } catch (ExpiredJwtException e) {
            log.error("Expired jwt", e);
            throw e;
        }
    }


}
