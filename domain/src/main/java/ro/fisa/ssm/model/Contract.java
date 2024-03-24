package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.fisa.ssm.enums.ContractDuration;
import ro.fisa.ssm.utils.AppDateUtils;

import java.time.LocalDate;

/**
 * Created at 3/18/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Contract extends SimpleContract {
    private Employee employee;
    private Employer employer;
    private Job job;
    private String number;
    private Double baseSalary;
    private Boolean fixedTerm;
    private Boolean activeStatus;
    private boolean hasErrors;

    public void enableErrors() {
        this.hasErrors = true;
        if (this.employee != null) {
            this.employee.enableErrors();
        }
    }

    public @SuppressWarnings("unused") LocalDate getDate() {
        try {
            final String dateStr = this.number.split("/")[1].trim();
            return AppDateUtils.toLocalDate(dateStr, "dd.MM.yyyy");
        } catch (Exception e) {
            return null;
        }
    }

    public void setJob(final String jobName) {

        this.job = new Job(jobName);
    }

    @Override
    public void setJob(final Job job) {
        this.job = job;
    }

    @Override
    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setActiveStatus(final String value) {
        switch (value.trim().toLowerCase()) {
            case "activ":
                this.activeStatus = true;
                break;
            case "incetat":
                this.activeStatus = false;
                break;
            default:
                this.enableErrors();
                break;
        }
    }

    public void setFixedTerm(final String type) {
        final String trimmed = type.trim();
        if (trimmed.equalsIgnoreCase(ContractDuration.FIXED_TERM.lowercaseValue())) {
            this.fixedTerm = true;
        } else if (trimmed.equalsIgnoreCase(ContractDuration.NON_FIXED_TERM.lowercaseValue())) {
            this.fixedTerm = false;
        } else {
            this.enableErrors();
        }
    }

    public boolean compare(Contract otherContract) {
        if (otherContract == null) {
            return false;
        }
        return this.getNumber().equals(otherContract.getNumber());
    }
}
