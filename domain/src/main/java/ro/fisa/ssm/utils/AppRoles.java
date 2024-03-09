package ro.fisa.ssm.utils;

import ro.fisa.ssm.model.Role;

import static ro.fisa.ssm.enums.RoleEnum.ADMIN;
import static ro.fisa.ssm.enums.RoleEnum.EMPLOYEE;

/**
 * Created at 3/9/2024 by Darius
 **/
public final class AppRoles {
    private AppRoles() {

    }
    public static Role ROLE_ADMIN = new Role(ADMIN.getId(), ADMIN.getName());
    public static Role ROLE_EMPLOYEE = new Role(EMPLOYEE.getId(), EMPLOYEE.getName());
}
