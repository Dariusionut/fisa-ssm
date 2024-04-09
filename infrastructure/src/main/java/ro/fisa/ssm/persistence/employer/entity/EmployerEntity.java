package ro.fisa.ssm.persistence.employer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.induction.entity.InductionEntity;
import ro.fisa.ssm.persistence.parents.VersionedEntity;
import ro.fisa.ssm.persistence.utils.DbConstants;

/**
 * Created at 3/15/2024 by Darius
 **/
@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.EMPLOYER, schema = DbConstants.Schemas.PUBLIC)
@SequenceGenerator(
        name = DbConstants.Sequences.EMPLOYER,
        sequenceName = DbConstants.Sequences.EMPLOYER,
        allocationSize = 1
)
public class EmployerEntity extends VersionedEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.EMPLOYER
    )
    protected Long id;

    @OneToOne(
            targetEntity = InductionEntity.class,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_induction", referencedColumnName = DbConstants.Column.ID)
    private InductionEntity induction;

    @Column(name = "name", nullable = false, length = DbConstants.Length.LENGTH_45)
    private String name;
    @Column(name = "cui_cif", length = DbConstants.Length.LENGTH_15)
    private String cuiCif;

    @Column(name = "caen", length = DbConstants.Length.LENGTH_200)
    private String caen;

}
