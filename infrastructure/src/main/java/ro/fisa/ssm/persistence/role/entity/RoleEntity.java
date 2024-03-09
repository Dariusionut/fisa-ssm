package ro.fisa.ssm.persistence.role.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.utils.DbConstants;

import static ro.fisa.ssm.persistence.utils.DbConstants.Length.*;

/**
 * Created at 3/9/2024 by Darius
 **/
@Getter
@Setter
@Entity
@Table(name = "app_role", schema = DbConstants.Schemas.PUBLIC)
public class RoleEntity {

    @Id
    private Short id;

    @Column(name = "name", length = LENGTH_15, nullable = false, updatable = false)
    private String name;
}
