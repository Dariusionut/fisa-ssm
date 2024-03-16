package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.fisa.ssm.utils.EmployeeUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created at 3/14/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends PrimaryModel<Long> {

    private Role role;
    private List<Contract> contracts = new ArrayList<>();
    private Nationality nationality;
    private String firstName;
    private String lastName;
    private String cnp;
    private String address;
    private boolean isActive;
    private boolean hasErrors;
    private boolean inductionAccepted;

    @SuppressWarnings("unused")
    public LocalDate getDateOfBirth() {
        if (this.cnp == null || this.cnp.isEmpty()) {
            return null;
        }
        try {
            return EmployeeUtils.extractDobFromCnp(this.cnp.trim());
        } catch (Exception e) {
            return null;
        }
    }

    public void enableErrors() {
        this.hasErrors = true;
    }

    @SuppressWarnings("unused")
    public String getFullName() {
        return String.format("%s %s", this.lastName, this.firstName);
    }
}
