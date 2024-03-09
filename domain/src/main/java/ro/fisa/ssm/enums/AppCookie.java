package ro.fisa.ssm.enums;

/**
 * Created at 3/9/2024 by Darius
 **/
public enum AppCookie {
    JSESSIONID("JSESSIONID");

    AppCookie(final String value){
        this.value = value;
    }

    private final String value;

    public String value() {
        return value;
    }
}
