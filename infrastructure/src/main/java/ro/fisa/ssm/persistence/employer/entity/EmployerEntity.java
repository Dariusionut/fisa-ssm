package ro.fisa.ssm.persistence.employer.entity;

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
    protected Integer id;

    @Column(name = "name", nullable = false, length = DbConstants.Length.LENGTH_45)
    private String name;
    @Column(name = "cui_cif")
    private String cuiCif;

    @Column(name = "caen_description")
    private String caenDescription;

    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;

}
