package ro.fisa.ssm.enums;

/**
 * Created at 3/9/2024 by Darius
 **/
public enum RoleEnum {

    EMPLOYEE((short) 1, "EMPLOYEE"),
    ADMIN((short) 2, "ADMIN");

    RoleEnum(final short id, final String name) {
        this.id = id;
        this.name = name;

    }
    private final short id;
    private final String name;

    public short getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
