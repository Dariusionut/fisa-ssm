package ro.fisa.ssm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created at 3/16/2024 by Darius
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeRegistryUtils {
    public static final int ROW_TO_START = 10;
    private static final int ROW_EMPLOYEE_TABLE = 10;
    public static final int EMPLOYER_DETAILS_CELL = 3;

    public static final int NAME_CELL = 2;
    public static final int CNP_CELL = 4;
    public static final int NATIONALITY_CELL = 5;
    public static final int ADDRESS_CELL = 7;
    public static final int CONTRACT_NUMBER_CELL = 8;
    public static final int CONTRACT_DURATION_TYPE_CELL = 10;
    public static final int JOB_NAME_CELL = 11;
    public static final int ACTIVE_CELL = 12;
    public static final int SALARY_CELL = 13;


}
