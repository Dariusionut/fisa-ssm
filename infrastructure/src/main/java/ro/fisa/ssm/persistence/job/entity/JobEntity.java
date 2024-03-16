package ro.fisa.ssm.persistence.job.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.parents.VersionedEntity;
import ro.fisa.ssm.persistence.utils.DbConstants;

/**
 * Created at 3/15/2024 by Darius
 **/
@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.JOB, schema = DbConstants.Schemas.PUBLIC)
@SequenceGenerator(
        name = DbConstants.Sequences.JOB,
        sequenceName = DbConstants.Sequences.JOB,
        allocationSize = 1
)
public class JobEntity extends VersionedEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.JOB
    )
    protected Integer id;

    @Column(name = DbConstants.Column.NAME, length = DbConstants.Length.LENGTH_200, unique = true, nullable = false)
    private String name;

}
