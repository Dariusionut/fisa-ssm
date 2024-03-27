package ro.fisa.ssm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.fisa.ssm.utils.AppStringUtils;
import ro.fisa.ssm.utils.EmployeeUtils;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created at 3/14/2024 by Darius
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends AuditableModel<Long> {

    private Role role;
    private Nationality nationality;
    private Collection<Contract> contracts;
    private String firstName;
    private String lastName;
    private String cnp;
    private String address;
    private String password;
    private boolean hasErrors;
    private boolean inductionAccepted;

    public @SuppressWarnings("unused") LocalDate getDateOfBirth() {
        if (AppStringUtils.isBlank(this.cnp)) {
            return null;
        }
        try {
            return EmployeeUtils.extractDobFromCnp(this.cnp.trim());
        } catch (Exception e) {
            this.setHasErrors(true);
            return null;
        }
    }

    public void setName(final String name) {
        final FullName fullName = new FullName(name);
        fullName.getFirstName().ifPresentOrElse(this::setFirstName, this::enableErrors);
        fullName.getLastName().ifPresentOrElse(this::setLastName, this::enableErrors);
    }

    public void enableErrors() {
        this.hasErrors = true;
    }

    @SuppressWarnings("unused")
    public String getFullName() {
        return String.format("%s %s", this.lastName, this.firstName);
    }
}
