package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created at 3/16/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Contract extends PrimaryModel<Long> {

    private Job job;
    private String number;
    private String baseSalary;
    private Boolean fixedTerm;
    private boolean activeStatus;
}
