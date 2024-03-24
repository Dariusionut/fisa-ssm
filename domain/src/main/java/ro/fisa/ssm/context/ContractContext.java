package ro.fisa.ssm.context;

/**
 * Created at 3/25/2024 by Darius
 **/
public record ContractContext(
        boolean ignoreEmployee,
        boolean ignoreEmployer,
        boolean ignoreJob
) {
}
