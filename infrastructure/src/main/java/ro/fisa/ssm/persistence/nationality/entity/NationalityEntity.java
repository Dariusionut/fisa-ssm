package ro.fisa.ssm.persistence.nationality.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.utils.DbConstants;

/**
 * Created at 3/15/2024 by Darius
 **/
@Getter
@Setter
@Entity
@Table(name = DbConstants.Table.NATIONALITY, schema = DbConstants.Schemas.PUBLIC)
@SequenceGenerator(
        name = DbConstants.Sequences.NATIONALITY,
        sequenceName = DbConstants.Sequences.NATIONALITY,
        allocationSize = 1
)
public class NationalityEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.NATIONALITY
    )
    protected Integer id;

    @Column(name = DbConstants.Column.NAME, nullable = false, length = DbConstants.Length.LENGTH_45)
    private String name;


}
