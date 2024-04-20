package ro.fisa.ssm.model;

import lombok.Builder;
import ro.fisa.ssm.enums.RoleEnum;

/**
 * Created at 4/7/2024 by Darius
 **/
@Builder
public record PrincipalDetails(
        Long id,
        String firstName,
        String lastName,
        RoleEnum role,
        String nationality
) {

}
