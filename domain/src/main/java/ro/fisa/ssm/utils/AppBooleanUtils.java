package ro.fisa.ssm.utils;

import java.util.function.Supplier;

/**
 * Created at 3/14/2024 by Darius
 **/
public final class AppBooleanUtils {

    private AppBooleanUtils() {
    }


    public static <T> T ifTrueOrElse(final boolean condition,
                                     final Supplier<T> trueSupplier,
                                     final Supplier<T> falseSupplier) {
        return condition ? trueSupplier.get() : falseSupplier.get();
    }
}
