package ro.fisa.ssm.enums;

/**
 * Created at 3/16/2024 by Darius
 **/
public enum ContractDuration {

    FIXED_TERM("determinata"),
    NON_FIXED_TERM("nedeterminata");


    ContractDuration(final String value) {
        this.value = value;
    }

    private final String value;

    public String value() {
        return value;
    }

    public String lowercaseValue() {
        return this.value().toLowerCase();
    }
}
