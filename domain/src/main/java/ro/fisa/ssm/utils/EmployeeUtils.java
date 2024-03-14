package ro.fisa.ssm.utils;

import java.time.LocalDate;

/**
 * Created at 3/14/2024 by Darius
 **/
public final class EmployeeUtils {

    private EmployeeUtils() {
    }

    public static boolean isCnpValid(final String cnp) {
        return cnp.length() == 13;
    }

    public static LocalDate extractDobFromCnp(final String cnp) {
        if (!isCnpValid(cnp)) {
            throw new IllegalStateException("Invalid CNP length");
        }

        int s = Character.getNumericValue(cnp.charAt(0));
        int year = Integer.parseInt(cnp.substring(1, 3));
        int month = Integer.parseInt(cnp.substring(3, 5));
        int day = Integer.parseInt(cnp.substring(5, 7));

        if (s == 1 || s == 2) { // 1900s
            year += 1900;
        } else if (s == 3 || s == 4) { // 1800s
            year += 1800;
        } else if (s == 5 || s == 6) { // 2000s
            year += 2000;
        } else {
            throw new IllegalArgumentException("Invalid CNP gender/century code.");
        }
        try {
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date in CNP.");
        }

    }
}
