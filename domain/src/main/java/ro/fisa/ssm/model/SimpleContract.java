package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created at 3/18/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SimpleContract extends AuditableModel<Long> {
    private Job job;
    private String number;
    private Double baseSalary;
    private Boolean fixedTerm;
    private boolean activeStatus;
}
