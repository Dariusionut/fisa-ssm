package ro.fisa.ssm.persistence.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.parents.Person;
import ro.fisa.ssm.persistence.role.entity.RoleEntity;
import ro.fisa.ssm.persistence.utils.DbConstants;

import static ro.fisa.ssm.persistence.utils.DbConstants.Column.ID;
import static ro.fisa.ssm.persistence.utils.DbConstants.Schemas.PUBLIC;

/**
 * Created at 3/9/2024 by Darius
 **/


@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.APP_USER, schema = PUBLIC)
@SequenceGenerator(
        name = DbConstants.Sequences.APP_USER,
        sequenceName = DbConstants.Sequences.APP_USER,
        allocationSize = 1
)
public class UserEntity extends Person {

    @ManyToOne(targetEntity = RoleEntity.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    @JoinColumn(name = "fk_role", referencedColumnName = ID)
    private RoleEntity role;

    @Column(name = "has_errors", nullable = false)
    private boolean hasErrors;

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.APP_USER
    )
    @Override
    public Long getId() {
        return super.getId();
    }

}
