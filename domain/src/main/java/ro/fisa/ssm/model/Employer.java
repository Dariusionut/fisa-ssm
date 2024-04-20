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
public class Employer extends VersionedModel<Long> {

    private String name;
    private String cuiCif;
    private String caen;
    private Induction induction;
    private String hrEmail;
    private int contractsCount;
}
