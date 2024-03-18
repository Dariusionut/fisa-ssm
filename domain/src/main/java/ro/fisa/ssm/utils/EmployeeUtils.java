package ro.fisa.ssm.utils;

import ro.fisa.ssm.exceptions.FullNameExtractException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created at 3/14/2024 by Darius
 **/
public final class EmployeeUtils {

    private EmployeeUtils() {
    }

    public static Optional<String> getFirstName(String fullName) {
        try {
            final String[] fullNameArr = splitFullName(fullName);
            if (fullNameArr.length == 2) {
                return Optional.ofNullable(fullNameArr[1]);
            } else {
                return Optional.ofNullable(String.format("%s-%s", fullNameArr[1], fullNameArr[2]));
            }
        } catch (FullNameExtractException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> getLastName(final String fullName) {
        try {
            return Optional.ofNullable(splitFullName(fullName)[0]);
        } catch (FullNameExtractException e) {
            return Optional.empty();
        }
    }

    public static String[] splitFullName(final String fullName) throws FullNameExtractException {
        try {
            return Arrays.stream(fullName.replaceAll("[()]", "")
                            .replace("-", " ")
                            .split(" "))
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .toArray(String[]::new);
        } catch (Exception e) {
            throw new FullNameExtractException("");
        }

    }

    public static boolean isCnpValid(final String cnp) {
        return cnp.length() == 13 && cnp.matches("\\d+");
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
