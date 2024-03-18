package ro.fisa.ssm.utils;

import java.util.function.Function;

/**
 * Created at 3/17/2024 by Darius
 **/
public final class AppObjectUtils {

    private AppObjectUtils() {
    }

    public static <T> boolean isPropertyNotBlank(T object, Function<T, String> stringPropertyGetter) {
        return object != null && AppStringUtils.isNotBlank(stringPropertyGetter.apply(object));
    }

    public static <T> boolean isObjectOrPropertyBlank(T t, Function<T, String> stringFunction) {
        return !isPropertyNotBlank(t, stringFunction);
    }

    public static <T> boolean isObjectOrPropertyNotNull(T t, Function<T, Object> stringFunction) {
        return t == null || stringFunction.apply(t) != null;
    }

    public static <T> boolean isObjectOrPropertyNull(T t, Function<T, Object> function) {
        return !isObjectOrPropertyNotNull(t, function);
    }

}
