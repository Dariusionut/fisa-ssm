package ro.fisa.ssm.utils;

import static java.lang.String.format;

/**
 * Created at 3/14/2024 by Darius
 **/
public final class DocumentUtils {

    private DocumentUtils() {
    }

    public static final class DocumentExtension {

        private DocumentExtension() {
        }

        public static final String XLS = "xls";
        public static final String DOT_XLS = format(".%s", XLS);
        public static final String XLSX = "xlsx";
        public static final String DOT_XLSX = format(".%s", XLSX);


    }
}
