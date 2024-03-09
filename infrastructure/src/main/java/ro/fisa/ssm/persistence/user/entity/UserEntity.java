package ro.fisa.ssm.persistence.user.entity;

import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.role.entity.RoleEntity;

/**
 * Created at 3/9/2024 by Darius
 **/

@Getter
@Setter
public class UserEntity {

    private Long id;
    private String cnp;
    private String password;

    private RoleEntity role;
}
