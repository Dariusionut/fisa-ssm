package ro.fisa.ssm.model;

import ro.fisa.ssm.utils.EmployeeUtils;

import java.util.Optional;

/**
 * Created at 3/14/2024 by Darius
 **/
public class FullName {

    private final String firstName;
    private final String lastName;

    public Optional<String> getFirstName() {
        return Optional.ofNullable(this.firstName);
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(this.lastName);
    }

    public FullName(String docFullName) {
        this.firstName = EmployeeUtils.getFirstName(docFullName).orElse(null);
        this.lastName = EmployeeUtils.getLastName(docFullName).orElse(null);
    }


}
