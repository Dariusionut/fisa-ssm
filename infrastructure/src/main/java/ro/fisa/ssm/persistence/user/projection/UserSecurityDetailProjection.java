package ro.fisa.ssm.persistence.user.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.enums.RoleEnum;

/**
 * Created at 4/6/2024 by Darius
 **/
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserSecurityDetailProjection {
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private String nationality;
    private String password;

    public RoleEnum getRole() {
        return RoleEnum.valueOf(this.role);
    }
}
