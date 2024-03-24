package ro.fisa.ssm.controller.params;

import lombok.Data;

/**
 * Created at 3/25/2024 by Darius
 **/
@Data
public class ContractApiParams {
    private Boolean ignoreEmployee = false;
    private Boolean ignoreEmployer = false;
    private Boolean ignoreJob = false;
}
