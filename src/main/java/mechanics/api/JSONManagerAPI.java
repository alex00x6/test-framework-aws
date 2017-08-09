package mechanics.api;

import mechanics.system.constant.AssembledEquipments;
import mechanics.system.http.JSONHandler;
import ru.yandex.qatools.allure.annotations.Step;

import static mechanics.system.constant.EmailInbox.*;

/**
 * Created by user on 20.04.2017.
 */
public class JSONManagerAPI extends JSONHandler {
    private static String thingVpv = AssembledEquipments.equipmentVpv;
    private static String thingGpv = AssembledEquipments.equipmentGpv;
    private String reportTemplateIdVPV_Daily = "Vacuum-Pump-Vibration-Report---Daily";
    private String reportTemplateIdVPV_Weekly = "Vacuum-Pump-Vibration-Report---Weekly";
    private String reportTemplateIdVPV_Monthly = "Vacuum-Pump-Vibration-Report---Monthly";
    private String reportTemplateIdGPV_Monthly = "GPV-Smart-Sensor-Report-15-minutes-activity";
    private String reportTemplateNameVPV_Daily = "Vacuum-Pump-Vibration-Report-List---Daily";
    private String reportTemplateNameVPV_Weekly = "Vacuum-Pump-Vibration-Report-List---Weekly";
    private String reportTemplateNameVPV_Monthly = "Vacuum-Pump-Vibration-Report-List---Monthly";
    private String reportTemplateNameGPV_Monthly = "GPV-Smart-Sensor-Report-List-15-minutes";

    @Step("Creating JSON for Abnormal Vibration rule,channel: Any, Abort.")
    public String jsonRuleAbnormalVibrationVPVAnyAbort() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleAbnormalVibrationsVPV.getMessage(),
                "Auto tests rule",
                0,
                0,
                thingVpv,
                0,
                "",
                ">=",
                30,
                0,
                1
        ).toString();
    }

    @Step("Creating JSON for Abnormal Vibration rule,channel: Any, Alarm.")
    public String jsonRuleAbnormalVibrationVPVAnyAlarm() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleAbnormalVibrationsVPV.getMessage(),
                "Auto tests rule",
                0,
                0,
                thingVpv,
                1,
                "",
                ">=",
                30,
                0,
                1
        ).toString();
    }

    @Step("Creating JSON for Abnormal Vibration rule,channel: Main, Abort.")
    public String jsonRuleAbnormalVibrationVPVMainAbort() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleAbnormalVibrationsVPV.getMessage(),
                "Auto tests rule",
                0,
                1,
                thingVpv,
                0,
                "",
                ">=",
                30,
                0,
                1
        ).toString();
    }

    @Step("Creating JSON for Abnormal Vibration rule,channel: Main, Alarm.")
    public String jsonRuleAbnormalVibrationVPVMainAlarm() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleAbnormalVibrationsVPV.getMessage(),
                "Auto tests rule",
                0,
                1,
                thingVpv,
                1,
                "",
                ">=",
                30,
                0,
                1
        ).toString();
    }

    @Step("Creating JSON for Abnormal Vibration rule,channel: Blower,Abort.")
    public String jsonRuleAbnormalVibrationVPVBlowerAbort() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleAbnormalVibrationsVPV.getMessage(),
                "Auto tests rule",
                0,
                2,
                thingVpv,
                0,
                "",
                ">=",
                30,
                0,
                1
        ).toString();
    }

    @Step("Creating JSON for Abnormal Vibration rule,channel: Blower,Alarm.")
    public String jsonRuleAbnormalVibrationVPVBlowerAlarm() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleAbnormalVibrationsVPV.getMessage(),
                "Auto tests rule",
                0,
                2,
                thingVpv,
                0,
                "",
                ">=",
                30,
                0,
                1
        ).toString();
    }

    @Step("Creating JSON for Number of Alarms above threshold on GPV equipment.")
    public String jsonRuleAlarmCountGPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleNumberOfAlarms.getMessage(),
                "Auto tests rule",
                3,
                "0",
                thingGpv,
                "",
                "",
                ">=",
                1,
                1,
                0
        ).toString();
    }

    @Step("Creating JSON for Number of Alarms above threshold on VPV equipment.")
    public String jsonRuleAlarmCountVPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleNumberOfAlarms.getMessage(),
                "Auto tests rule",
                3,
                "0",
                thingVpv,
                "",
                "",
                ">=",
                1,
                1,
                0
        ).toString();
    }

    @Step("Creating JSON for Number of Aborts above threshold on Pump equipment.")
    public String jsonRuleAbortsCountVPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleNumberOfAborts.getMessage(),
                "Auto tests rule",
                2,
                "0",
                thingVpv,
                "",
                "",
                ">=",
                1,
                1,
                0
        ).toString();
    }

    @Step("Creating JSON for Disconnected rule for VPV equipment.")
    public String jsonRuleDisconnectedVPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleDisconnectedVPV.getMessage(),
                "Auto tests rule",
                4,
                0,
                thingVpv,
                0,
                "0",
                "",
                0,
                0,
                0
        ).toString();
    }

    @Step("Creating JSON for Disconnected rule for BUC ARTRobot.")
    public String jsonRuleDisconnectedGPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleDisconnectedGPV.getMessage(),
                "Auto tests rule",
                4,
                0,
                thingGpv,
                0,
                "0",
                "",
                0,
                0,
                0
        ).toString();
    }

    @Step("Creating JSON for Connected rule for VPV equipment.")
    public String jsonRuleConnectedVPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleConnectedVPV.getMessage(),
                "Auto tests rule",
                4,
                0,
                thingVpv,
                0,
                "1",
                "",
                0,
                0,
                0
        ).toString();
    }

    @Step("Creating JSON for Connected rule for GPV equipment.")
    public String jsonRuleConnectedGPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleConnectedGPV.getMessage(),
                "Auto tests rule",
                4,
                0,
                thingGpv,
                0,
                "1",
                "",
                0,
                0,
                0
        ).toString();
    }

    @Step("Creating JSON for Every Warning rule for VPV equipment.")
    public String jsonRuleEveryWarningVPVAbort() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleEveryWarningVPV.getMessage(),
                "Auto tests rule",
                8,
                0,
                thingVpv,
                0,
                "",
                ">=",
                0,
                0,
                0
        ).toString();
    }

    @Step("Creating JSON for Every Warning rule for VPV equipment.")
    public String jsonRuleEveryWarningVPVAlarm() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleEveryWarningVPV.getMessage(),
                "Auto tests rule",
                8,
                0,
                thingVpv,
                1,
                "",
                ">=",
                0,
                0,
                0
        ).toString();
    }

    @Step("Creating JSON for Every Warning rule for GPV equipment.")
    public String jsonRuleEveryWarningGPV() {
        return notificationRuleCreateJSON(
                SubjectNotificationRuleEveryWarningGPV.getMessage(),
                "Auto tests rule",
                9,
                0,
                thingGpv,
                0,
                "",
                ">=",
                0,
                0,
                0
        ).toString();
    }

    public String jsonEquipmentDisconnectVPV() {
        return equipmentOnOff(thingVpv, false);
    }

    public String jsonEquipmentConnectVPV() {
        return equipmentOnOff(thingVpv, true);
    }

    public String jsonEquipmentDisconnectGPV() {
        return equipmentOnOff(thingGpv, false);
    }

    public String jsonEquipmentConnectGPV() {
        return equipmentOnOff(thingGpv, true);
    }

    public String JSONReportCreateVPV_Daily() {
        return reportCreate(reportTemplateIdVPV_Daily, thingVpv, reportTemplateNameVPV_Daily).toString();
    }

    public String JSONReportCreateVPV_Weekly() {
        return reportCreate(reportTemplateIdVPV_Weekly, thingVpv, reportTemplateNameVPV_Weekly).toString();
    }

    public String JSONReportCreateVPV_Monthly() {
        return reportCreate(reportTemplateIdVPV_Monthly, thingVpv, reportTemplateNameVPV_Monthly).toString();
    }

    public String JSONReportCreateGPV_Monthly() {
        return reportCreate(reportTemplateIdGPV_Monthly, thingGpv, reportTemplateNameGPV_Monthly).toString();
    }

    public String JSONReportSendNowVPV_Daily(String id) {
        return reportSendNow(id, reportTemplateIdVPV_Daily, thingVpv, reportTemplateNameVPV_Daily).toString();
    }

    public String JSONReportSendNowVPV_Weekly(String id) {
        return reportSendNow(id, reportTemplateIdVPV_Weekly, thingVpv, reportTemplateNameVPV_Weekly).toString();
    }

    public String JSONReportSendNowVPV_Monthly(String id) {
        return reportSendNow(id, reportTemplateIdVPV_Monthly, thingVpv, reportTemplateNameVPV_Monthly).toString();
    }

    public String JSONReportSendNowGPV_Monthly(String id) {
        return reportSendNow(id, reportTemplateIdGPV_Monthly, thingGpv, reportTemplateNameGPV_Monthly).toString();
    }

    public String JSONReportDelete(String filter_name, String filterId, String userId) {
        return reportDelete(filter_name, filterId, userId).toString();
    }


}
