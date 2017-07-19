package mechanics.system.credentials;

import mechanics.system.jar.Args;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Storm on 08.06.2017.
 */
public class Users {
    public volatile static User system_admin = new User();
    public volatile static User admin = new User();
    public volatile static User regular = new User();
    public volatile static User amazon_regular = new User();

    public static void initializeAwsKeys() {
        List<String> roles = Args.role;
        for (int i = 0; i < roles.size(); i++) {
            Collector collector = new Collector();
            collector.receiveAWSKeys(roles.get(i));
        }
    }

    public static void initializeCreds() {
//        List<String> roles = Args.role;
        List<String> roles = new ArrayList<>();
        roles.add("system_admin");
        roles.add("admin");
        roles.add("regular");
        roles.add("amazon_regular");
        for (int i = 0; i < roles.size(); i++) {
            Collector collector = new Collector();
            collector.readEmailPassword(roles.get(i));
        }
    }


    public static User getUser(String user) {
        switch (user) {
            case "system_admin":
                return system_admin;
            case "admin":
                return admin;
            case "regular":
                return regular;
            case "amazon_regular":
                return amazon_regular;
            default:
                return null;
        }
    }

}
