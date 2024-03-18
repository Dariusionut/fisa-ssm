package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created at 3/17/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VersionedModel<T extends Number> extends PrimaryModel<T> {

    private int version = 0;
}
