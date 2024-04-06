package ro.fisa.ssm.model;

import lombok.Builder;

/**
 * Created at 4/7/2024 by Darius
 **/
@Builder
public record PrincipalDetails(
        String username,
        String firstName,
        String lastName,
        String role,
        String nationality
) {
}
