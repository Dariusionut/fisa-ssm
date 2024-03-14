package ro.fisa.ssm.model;

import java.util.Arrays;

/**
 * Created at 3/14/2024 by Darius
 **/
public class Fullname {

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Fullname(String docFullName) {
        final String[] fullName = Arrays.stream(docFullName.replaceAll("[()]", "")
                        .replace("-", " ")
                        .split(" "))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .toArray(String[]::new);
        if (fullName.length == 2) {
            this.lastName = fullName[0];
            this.firstName = fullName[1];
        } else {
            this.lastName = fullName[0];
            this.firstName = String.format("%s-%s", fullName[1], fullName[2]);
        }
    }


}
