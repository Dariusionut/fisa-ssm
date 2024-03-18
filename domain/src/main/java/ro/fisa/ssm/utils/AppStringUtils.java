package ro.fisa.ssm.utils;

/**
 * Created at 3/17/2024 by Darius
 **/
public final class AppStringUtils {

    private AppStringUtils() {
    }

    public static boolean isBlank(final String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }
}
