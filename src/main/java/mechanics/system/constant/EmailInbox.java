package mechanics.system.constant;

/**
 * Created by Kutafin Oleg on 24.04.2017.
 */
public enum EmailInbox implements CharSequence {

    SubjectNotificationRuleAbnormalVibrationsVPV("Abnormal Vibration VPV"),
    SubjectNotificationRuleNumberOfAlarms("Number of Alarms above threshold"),
    SubjectNotificationRuleNumberOfAborts("Number of Aborts above threshold"),
    SubjectNotificationRuleDisconnectedVPV("Disconnected Equipment VPV"),
    SubjectNotificationRuleDisconnectedGPV("Disconnected Equipment GPV"),
    SubjectNotificationRuleConnectedVPV("Connected Equipment VPV"),
    SubjectNotificationRuleConnectedGPV("Connected Equipment GPV"),
    SubjectNotificationRuleEveryWarningVPV("Every Warning VPV"),
    SubjectNotificationRuleEveryWarningGPV("Every Warning GPV"),


    SubjectReportListVPVDaily("Vacuum Pump Vibration Report - Daily"),
    SubjectReportListVPVWeekly("Vacuum Pump Vibration Report - Weekly"),
    SubjectReportListVPVMonthly("Vacuum Pump Vibration Report - Monthly"),
    SubjectReportListGPV15minutes("GPV Smart Sensor Report (15 minutes activity)"),

    ReportFileNameVPVWeekly("Vacuum-Pump-Vibration-Report---Weekly-Vacuum-Pump-Vibration-Report-List---Weekly");

    private final String Message;

    EmailInbox(String m) {
        Message = m;
    }

    public String getMessage() {
        return Message;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }
}
