package ro.fisa.ssm.persistence.nationality.entity;

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
@Table(name = DbConstants.Table.NATIONALITY, schema = DbConstants.Schemas.PUBLIC)
@SequenceGenerator(
        name = DbConstants.Sequences.NATIONALITY,
        sequenceName = DbConstants.Sequences.NATIONALITY,
        allocationSize = 1
)
public class NationalityEntity extends VersionedEntity<Integer> {

    @Column(name = DbConstants.Column.NAME, nullable = false, length = DbConstants.Length.LENGTH_45)
    private String name;

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = DbConstants.Sequences.NATIONALITY
    )
    @Override
    public Integer getId() {
        return super.getId();
    }
}
