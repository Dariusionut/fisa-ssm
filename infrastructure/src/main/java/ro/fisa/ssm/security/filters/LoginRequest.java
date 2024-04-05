package ro.fisa.ssm.security.filters;

/**
 * Created at 4/6/2024 by Darius
 **/
public record LoginRequest(
        String username,
        String password
) {
}
