package ro.fisa.ssm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created at 3/24/2024 by Darius
 **/
public final class AppDateUtils {

    private AppDateUtils(){}


    public static LocalDate toLocalDate(final String txt, final String pattern){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(txt, formatter);
    }
}
