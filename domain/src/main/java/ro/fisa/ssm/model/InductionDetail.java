package ro.fisa.ssm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created at 4/20/2024 by Darius
 **/
@Getter
@Setter
public class InductionDetail {
    private long contractId;
    private boolean isAccepted;
    private String contractNo;
    private String employerName;
    private LocalDateTime lastUpdate;
    private EmployeeSimpleDetails employee;
}
