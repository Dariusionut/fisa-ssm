package ro.fisa.ssm.persistence.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created at 3/10/2024 by Darius
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Table {
        public static final String JOB = "job";
        public static final String CONTRACT = "contract";
        public static final String APP_ROLE = "app_role";
        public static final String APP_USER = "app_user";
        public static final String EMPLOYER = "employer";
        public static final String NATIONALITY = "nationality";

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Sequences {

        public static final String APP_USER = "seq_app_user";
        public static final String EMPLOYER = "seq_employer";
        public static final String JOB = "seq_job";
        public static final String NATIONALITY = "seq_nationality";
        public static final String CONTRACT = "seq_contract";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Schemas {

        public static final String PUBLIC = "public";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Length {

        public static final int LENGTH_15 = 15;

        public static final int LENGTH_13 = 13;

        public static final int LENGTH_100 = 100;
        public static final int LENGTH_45 = 45;
        public static final int LENGTH_65 = 65;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Column {

        public static final String ID = "id";
        public static final String NAME = "name";
    }

}
