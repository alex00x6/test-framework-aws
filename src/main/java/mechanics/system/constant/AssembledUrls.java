package mechanics.system.constant;

import static mechanics.system.constant.Routes.*;

/**
 * Created by Alex Storm on 16.05.2017.
 */
public class AssembledUrls {
    public static String apiUrl;
    public static String apiUrlMin;
    public static String redirectClientURI;
    public static String iotEndpoint;
    public static String skedlerEndpoint;

    public static String notificationRule;
    public static String notificationUnread;
    public static String notification;
    public static String equipmentAdmin;
    public static String equipment;
    public static String report;
    public static String authenticationRefresh;
    public static String dashboard;
    public static String chart;
    public static String globalSettings;

    public static String userList;
    public static String user;

    public void assemble() {
        notificationRule = apiUrl + NotificationRule.getValue();
        notificationUnread = apiUrl + NotificationUnread.getValue();
        notification = apiUrl + Notification.getValue();
        equipmentAdmin = apiUrl + EquipmentAdmin.getValue();
        equipment = apiUrl + Equipment.getValue();
        report = apiUrl + Report.getValue();
        authenticationRefresh = apiUrl + AuthenticationRefresh.getValue();
        dashboard = apiUrl + Dashboard.getValue();
        chart = apiUrl + Chart.getValue();
        globalSettings = apiUrl + GlobalSettings.getValue();
        userList = apiUrl+UserList.getValue();
        user = apiUrl+User.getValue();

    }

    public void setApiUrl(String apiUrl) {
        AssembledUrls.apiUrl = apiUrl;
    }

    public void setApiUrlMin(String apiUrlMin) {
        AssembledUrls.apiUrlMin = apiUrlMin;
    }

    public void setRedirectClientURI(String redirectClientURI) {
        AssembledUrls.redirectClientURI = redirectClientURI;
    }

    public void setIotEndpoint(String iotEndpoint) {
        AssembledUrls.iotEndpoint = iotEndpoint;
    }

    public void setSkedlerEndpoint(String skedlerEndpoint) {
        AssembledUrls.skedlerEndpoint = skedlerEndpoint;
    }


}
