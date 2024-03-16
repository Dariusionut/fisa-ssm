package ro.fisa.ssm.persistence.contract.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.job.entity.JobEntity;
import ro.fisa.ssm.persistence.parents.VersionedEntity;
import ro.fisa.ssm.persistence.utils.DbConstants;

/**
 * Created at 3/15/2024 by Darius
 **/
@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.CONTRACT, schema = DbConstants.Schemas.PUBLIC)
@SequenceGenerator(
        name = DbConstants.Sequences.CONTRACT,
        sequenceName = DbConstants.Sequences.CONTRACT,
        allocationSize = 1
)
public class ContractEntity extends VersionedEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.CONTRACT
    )
    protected Long id;

    @ManyToOne(targetEntity = JobEntity.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    @JoinColumn(name = "fk_job", referencedColumnName = DbConstants.Column.ID)
    private JobEntity job;

//    @ManyToOne(targetEntity = EmployerEntity.class,
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.MERGE}
//    )
//    @JoinColumn(name = "fk_employer", referencedColumnName = DbConstants.Column.ID)
//    private EmployerEntity employer;

    @Column(name = "number")
    private String number;
//    @Column(name = "base_salary")
//    private Double baseSalary;
    @Column(name = "fixed_term")
    private boolean fixedTerm;
//    @Column(name = "active_status")
//    private boolean activeStatus;

}
