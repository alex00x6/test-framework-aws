package mechanics.system.constant;

/**
 * Created by Alex Storm on 27.07.2017.
 */
public enum StatusICT {

    UNKNOWN("online"),
    INIT("unknown"),
    IDLE("idle"),
    WORKING("working"),
    PAUSE("pause"),
    ERROR("error"),
    STOPPING("stopping"),
    UNREACHABLE("offline");

    private final String value;

    StatusICT(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}
