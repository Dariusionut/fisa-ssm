package ro.fisa.ssm.persistence.role.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.parents.PrimaryEntity;
import ro.fisa.ssm.persistence.utils.DbConstants;

import static ro.fisa.ssm.persistence.utils.DbConstants.Length.LENGTH_15;

/**
 * Created at 3/9/2024 by Darius
 **/
@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.APP_ROLE, schema = DbConstants.Schemas.PUBLIC)
public class RoleEntity extends PrimaryEntity<Short> {

    @Column(name = DbConstants.Column.NAME, length = LENGTH_15, nullable = false, updatable = false, insertable = false)
    private String name;
}
