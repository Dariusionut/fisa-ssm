package ro.fisa.ssm.enums;

/**
 * Created at 3/27/2024 by Darius
 **/
public enum ContractStatusEnum {
    ACTIVE((short) 1),
    INACTIVE((short) 2),
    SUSPENDED((short) 3);

    ContractStatusEnum(final short id) {
        this.id = id;
    }

    private final short id;

    public short id() {
        return this.id;
    }
}
