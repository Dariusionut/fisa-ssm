package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.fisa.ssm.enums.ContractDuration;
import ro.fisa.ssm.enums.ContractStatusEnum;
import ro.fisa.ssm.utils.AppDateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created at 3/18/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Contract extends AuditableModel<Long> {
    private Employee employee;
    private Employer employer;
    private Job job;
    private String number;
    private Double baseSalary;
    private Boolean fixedTerm;
    private ContractStatusEnum status;
    private LocalDateTime inductionAcceptedAt;
    private boolean hasErrors;

    public boolean isNotInactive() {
        return this.status != null && !this.status.equals(ContractStatusEnum.INACTIVE);
    }

    public void enableErrors(String reason) {
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

    public void setJob(final Job job) {
        this.job = job;
    }

    public void setStatus(final ContractStatusEnum status) {
        this.status = status;
    }

    public void setStatus(final String value) {
        switch (value.trim().toLowerCase()) {
            case "activ":
                this.status = ContractStatusEnum.ACTIVE;
                break;
            case "incetat":
                this.status = ContractStatusEnum.INACTIVE;
                break;
            case "suspendat":
                this.status = ContractStatusEnum.SUSPENDED;
                break;
            default:
                this.enableErrors("Contract status error");
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
            this.enableErrors("Fixed term error");
        }
    }

    public void setFixedTerm(final Boolean fixedTerm) {
        this.fixedTerm = fixedTerm;
    }

    public boolean compare(Contract otherContract) {
        if (otherContract == null) {
            return false;
        }
        return this.getNumber().equalsIgnoreCase(otherContract.getNumber());
    }
}
