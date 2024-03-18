package ro.fisa.ssm.utils;

import java.util.Optional;

/**
 * Created at 3/17/2024 by Darius
 **/
public final class OptionalUtils {

    public static <T>Optional<T> getOptional(T t){
        return Optional.ofNullable(t);
    }
}
