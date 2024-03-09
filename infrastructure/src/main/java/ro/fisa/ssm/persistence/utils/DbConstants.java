package ro.fisa.ssm.persistence.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created at 3/10/2024 by Darius
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbConstants {

    public static class Sequences {
        public static final String APP_USER = "seq_app_user";
        public static final String EMPLOYER = "seq_employer";
        public static final String COR = "seq_cor";
        public static final String NATIONALITY = "seq_nationality";
        public static final String CONTRACT = "seq_contract";
    }

    public static class Schemas {
        public static final String PUBLIC = "public";
    }

    public static class Length {
        public static final int LENGTH_15 = 15;

        public static final int LENGTH_13 = 13;

        public static final int LENGTH_100 = 100;
    }

    public static class Column {
        public static final String ID = "id";
    }

}
