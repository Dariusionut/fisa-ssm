package ro.fisa.ssm.persistence.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;
import ro.fisa.ssm.persistence.nationality.entity.NationalityEntity;
import ro.fisa.ssm.persistence.parents.Person;
import ro.fisa.ssm.persistence.role.entity.RoleEntity;
import ro.fisa.ssm.persistence.utils.DbConstants;

import java.time.LocalDateTime;
import java.util.List;

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

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.APP_USER
    )
    protected Long id;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    @JoinTable(
            schema = PUBLIC,
            name = "app_user_contract",
            joinColumns = {
                    @JoinColumn(name = "fk_app_user", referencedColumnName = ID),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "fk_contract", referencedColumnName = ID)
            }
    )
    private List<ContractEntity> contracts;

    @ManyToOne(targetEntity = RoleEntity.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    @JoinColumn(name = "fk_role", referencedColumnName = ID)
    private RoleEntity role;

    @ManyToOne(targetEntity = NationalityEntity.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    @JoinColumn(name = "fk_nationality", referencedColumnName = ID)
    private NationalityEntity nationality;

    @Column(name = "has_errors", nullable = false)
    private boolean hasErrors;

    @Column(name = "induction_accepted", nullable = false)
    private boolean inductionAccepted;

    @PrePersist
    private void prePersist() {
        this.setCreatedAt(LocalDateTime.now());
    }
}
