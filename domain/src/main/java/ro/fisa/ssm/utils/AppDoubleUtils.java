package ro.fisa.ssm.utils;

/**
 * Created at 3/17/2024 by Darius
 **/
public final class AppDoubleUtils {

    private AppDoubleUtils() {
    }

    public static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
