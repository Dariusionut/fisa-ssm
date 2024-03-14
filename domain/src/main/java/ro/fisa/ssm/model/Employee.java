package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.fisa.ssm.utils.EmployeeUtils;

import java.time.LocalDate;

/**
 * Created at 3/14/2024 by Darius
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Employee {

    private String firstName;
    private String lastName;
    private String cnp;
    private boolean isActive;

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

    @SuppressWarnings("unused")
    public String getFullName() {
        return String.format("%s %s", this.lastName, this.firstName);
    }
}
