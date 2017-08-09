package mechanics.system.credentials;

import java.util.List;

/**
 * Created by Alex Storm on 05.06.2017.
 */
public class RoleSwitcher {
    public static List<String> roles;
    private static String currentRole;
    private static int counter = 0;

    public static void initializeCurrentUser() {

    }

    public static int getRolesCount() {
        return roles.size();
    }

    public static String getCurrentRoleName() {
        if (counter <= roles.size() - 1) {
            currentRole = roles.get(counter);
        }
        return currentRole;
    }

    public static void roleFinished() {
        counter++;
    }


    public static User getCurrentUser() {
        return Users.getUser(getCurrentRoleName());
    }
}
