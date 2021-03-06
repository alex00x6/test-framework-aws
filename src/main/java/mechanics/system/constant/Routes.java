package mechanics.system.constant;

/**
 * Created by Alex Storm on 16.05.2017.
 */
public enum Routes {
    //available from UI
    NotificationRule("rule"),
    NotificationUnread("notification?status=unread"),
    Notification("notification"),
    EquipmentAdmin("equipment_admin"),
    Equipment("equipment"),
    Report("report"),
    AuthenticationRefresh("authentication/refresh"),
    Dashboard("dashboard"),
    Chart("chart"),
    GlobalSettings("global_settings"),
    //available from API
    UserList("user?typeList=users"),
    User("user");

    private final String value;

    Routes(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }

    public String getValue(String suffix) {
        return value + suffix;
    }
}
