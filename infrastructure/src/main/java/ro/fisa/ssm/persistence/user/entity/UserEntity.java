package ro.fisa.ssm.persistence.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.role.entity.RoleEntity;
import ro.fisa.ssm.persistence.utils.DbConstants;

import static ro.fisa.ssm.persistence.utils.DbConstants.Column.ID;
import static ro.fisa.ssm.persistence.utils.DbConstants.Length.LENGTH_100;
import static ro.fisa.ssm.persistence.utils.DbConstants.Length.LENGTH_13;
import static ro.fisa.ssm.persistence.utils.DbConstants.Schemas.PUBLIC;

/**
 * Created at 3/9/2024 by Darius
 **/

@Getter
@Setter
@Entity
@Table(name = "app_user", schema = PUBLIC)
public class UserEntity {

    @Id
    @SequenceGenerator(
            name = DbConstants.Sequences.APP_USER,
            sequenceName = DbConstants.Sequences.APP_USER,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.APP_USER
    )
    private Long id;

    @Column(name = "cnp", length = LENGTH_13, nullable = false)
    private String cnp;
    @Column(name = "email", length = LENGTH_100)
    private String email;
    @Column(name = "password", length = LENGTH_100, nullable = false)
    private String password;

    @ManyToOne(targetEntity = RoleEntity.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    @JoinColumn(name = "fk_role", referencedColumnName = ID)
    private RoleEntity role;
}
