package ro.fisa.ssm.persistence.parents;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import ro.fisa.ssm.persistence.utils.DbConstants;

/**
 * Created at 3/15/2024 by Darius
 **/


@Getter
@Setter
@MappedSuperclass
public class PrimaryEntity<T extends Number> {

    @Id
    @Column(name = DbConstants.Column.ID, nullable = false, unique = true)
    protected T id;


}
