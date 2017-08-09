package mechanics.api;

import com.jayway.restassured.response.Response;
import mechanics.api.json.Dashboard;
import mechanics.system.constant.AssembledEquipments;
import mechanics.system.constant.AssembledUrls;
import mechanics.system.http.JSONHandler;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.Map;

import static mechanics.system.constant.HTTPMethod.*;

/**
 * Created by user on 20.04.2017.
 */
public class RequestManagerAPI extends JSONManagerAPI {
    public static String idOfCreatedNotificationRule = null;
    private static String idOfCreatedReport = null;
    private static String idOfUser = null;
    private static String idOfCreatedDashboard = null;
    private static String filter_name = null;


    private static String dashboard = AssembledUrls.dashboard;

    private static String thingVPVForDashboard = AssembledEquipments.equipmentVpv;
    private static String thingGPVForDashboard = AssembledEquipments.equipmentGpv;


    private static String defaultMain = "freqMain";
    private static String defaultBlower = "freqBlower";
    private static String newMain = "testMain";
    private static String newBlower = "testBlower";

    public RequestManagerAPI() {
        messagesEnableAllDebugResponse = true;
        messagesEnableErrorDebugResponse = false;
        messagesEnableGatlingReport = false;
    }

    public String getYieldInPeriodValue(String equipmentId){
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.apiUrl+"chart/"+equipmentId+"?timeType=day");
        return getYieldInPeriod(response.asString());
    }

    public String getLastYieldPointValue(String equipmentId){
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.apiUrl+"chart/"+equipmentId+"?timeType=day");
        return getLastYieldPoint(response.asString());
    }

    public Response hardDeleteUser(String email, String role){
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.userList);
        String userId = new JSONHandler().getUserIdFromUserList(response.asString(), email, role);
        String body = "{ \"id\":\""+userId+"\", \"isHardDelete\": true }";
        return sendAmazonRequest(DELETE.getValue(), AssembledUrls.user, body);
    }


    public Response getGlobalSettings() {
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.globalSettings);
        checkResponse(response);
        return response;
    }

    public Response setGlobalSettings(String body) {
        Response response = sendAmazonRequest(PUT.getValue(), AssembledUrls.globalSettings, body);
        checkResponse(response);
        return response;
    }

    public void getRoute(String[] routes) {

        for (int i = 0; i < routes.length; i++) {
            Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.apiUrl + routes[i]);
            checkResponseSoft(response);
        }

    }


    public void authenticationRefresh() {
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.authenticationRefresh);
        checkResponse(response);
    }

    @Step("Creating report VPV Daily")
    public Response skedlerReportCreateVPV_Daily() {
        String jsonBody = JSONReportCreateVPV_Daily();
        Response response = sendAmazonRequest(PUT.getValue(), AssembledUrls.report, jsonBody);
        idOfCreatedReport = getIdOfCreatedReport(response.asString());
        idOfUser = getIdOfUser(response.asString());
        filter_name = getFilter_name(response.asString());
        checkResponse(response);
        return response;
    }

    @Step("Creating report VPV Weekly")
    public Response skedlerReportCreateVPV_Weekly() {
        String jsonBody = JSONReportCreateVPV_Weekly();
        Response response = sendAmazonRequest(PUT.getValue(), AssembledUrls.report, jsonBody);
        idOfCreatedReport = getIdOfCreatedReport(response.asString());
        idOfUser = getIdOfUser(response.asString());
        filter_name = getFilter_name(response.asString());
        checkResponse(response);
        return response;
    }

    @Step("Creating report VPV Monthly")
    public Response skedlerReportCreateVPV_Monthly() {
        String jsonBody = JSONReportCreateVPV_Monthly();
        Response response = sendAmazonRequest(PUT.getValue(), AssembledUrls.report, jsonBody);
        idOfCreatedReport = getIdOfCreatedReport(response.asString());
        idOfUser = getIdOfUser(response.asString());
        filter_name = getFilter_name(response.asString());
        checkResponse(response);
        return response;
    }

    @Step("Creating report GPV last 15 minutes ")
    public Response skedlerReportCreateGPV_Monthly() {
        String jsonBody = JSONReportCreateGPV_Monthly();
        Response response = sendAmazonRequest(PUT.getValue(), AssembledUrls.report, jsonBody);
        idOfCreatedReport = getIdOfCreatedReport(response.asString());
        idOfUser = getIdOfUser(response.asString());
        filter_name = getFilter_name(response.asString());
        checkResponse(response);
        return response;
    }

    @Step("Sending report now.")
    public void skedlerReportSendNowVPV_Daily() {
        String jsonBody = JSONReportSendNowVPV_Daily(idOfCreatedReport);
        Response response = sendAmazonRequest(POST.getValue(), AssembledUrls.report, jsonBody);
        checkResponse(response);
    }

    @Step("Sending report now.")
    public void skedlerReportSendNowVPV_Weekly() {
        String jsonBody = JSONReportSendNowVPV_Weekly(idOfCreatedReport);
        Response response = sendAmazonRequest(POST.getValue(), AssembledUrls.report, jsonBody);
        checkResponse(response);
    }

    @Step("Sending report now.")
    public void skedlerReportSendNowVPV_Monthly() {
        String jsonBody = JSONReportSendNowVPV_Monthly(idOfCreatedReport);
        Response response = sendAmazonRequest(POST.getValue(), AssembledUrls.report, jsonBody);
        checkResponse(response);
    }

    @Step("Sending report now.")
    public void skedlerReportSendNowGPV_Monthly() {
        String jsonBody = JSONReportSendNowGPV_Monthly(idOfCreatedReport);
        Response response = sendAmazonRequest(POST.getValue(), AssembledUrls.report, jsonBody);
        checkResponse(response);
    }

    public void skedlerReportDelete() {
        String jsonBody = JSONReportDelete(filter_name, idOfCreatedReport, idOfUser);
        //get body from test account
        //{"filter_name":"GPV-Smart-Sensor-Report-List-15-minutes","filterTitle":"GPV-Smart-Sensor-Report-List-15-minutes hom.ossystem@gmail.com","equipments":"Thing-090035-0","id":null,"filterId":"7e15db45-45f3-4f41-8979-bd35787be667","emails":"hom.ossystem@gmail.com","userId":"0315f51c-67ab-4390-bdd1-46bd9d3fd038","createdAt":null,"excelIncluded":null}
        Response response = sendAmazonRequest(DELETE.getValue(), AssembledUrls.report, jsonBody);
        checkResponse(response);
    }


    @Step("Deleting all notifications.")
    public void notificationListDeleteAll() {
        ArrayList<String> ids = getIdsOfAllNotifications(notificationListRead().asString());
        String jsonBody = notificationListDeleteAllJSON(ids);
        Response response = sendAmazonRequest(DELETE.getValue(), AssembledUrls.notification, jsonBody);
        checkResponse(response);
    }

    @Step("Deleting all notifications.")
    public void notificationListDeleteAllAssertless() {
        ArrayList<String> ids = getIdsOfAllNotifications(notificationListRead().asString());
        if (ids != null && !ids.isEmpty()) {
            String jsonBody = notificationListDeleteAllJSON(ids);
            Response response = sendAmazonRequest(DELETE.getValue(), AssembledUrls.notification, jsonBody);
            checkStatusCode(response);
        }
    }

    @Step("Changing state of equipment Connected/Disconnected")
    public Response equipmentChangeState(String jsonBody) {
        Response response = sendAmazonRequest(PUT.getValue(), AssembledUrls.equipmentAdmin, jsonBody);
        checkResponse(response);
        return response;

    }

    @Step("Creating rule.")
    public Response notificationRuleCreate(String jsonBody) {
        Response response = sendAmazonRequest(POST.getValue(), AssembledUrls.notificationRule, jsonBody);
        idOfCreatedNotificationRule = getIdOfCreatedNotificationRule(response.asString());
        checkResponse(response);
        return response;
    }

    @Step("Check if rule created.")
    public Response checkNotificationRuleIsCreated() {
        Response response = notificationRulesRead();
        checkResponse(response);
        Assert.assertTrue(response.asString().contains(idOfCreatedNotificationRule));
        return response;
    }

    @Step("Check if Rule not triggered.")
    public void checkNotificationRuleNotTriggered() {
        Assert.assertTrue(!notificationListRead().asString().contains(idOfCreatedNotificationRule));
    }

    @Step("Check if Rule not triggered.")
    public void checkNotificationRuleNotTriggeredLong() {
        sleep(5000);
        Assert.assertTrue(!notificationListRead().asString().contains(idOfCreatedNotificationRule));
    }


    @Step("Check if rule triggered.")
    public void checkNotificationRuleTriggeredLong() {
        if (notificationListRead().asString().contains(idOfCreatedNotificationRule)) {
            Assert.assertTrue(true);
        } else {
            sleep(5000);
            if (notificationListRead().asString().contains(idOfCreatedNotificationRule)) {
                Assert.assertTrue(true);
            } else {
                sleep(5000);
                Assert.assertTrue(notificationListRead().asString().contains(idOfCreatedNotificationRule));
            }
        }
    }

    public Response notificationGetUnread() {
        Map<String, String> headers = allHeaders(GET.getValue(), AssembledUrls.notificationUnread);
        Response response = createEmptyRequestWithHeaders(headers).get(AssembledUrls.notificationUnread).getResponse();
        checkResponse(response);
        return response;
    }

    public Response notificationRulesRead() {
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.notificationRule);
        checkResponse(response);
        return response;
    }

    public Response notificationRulesRead(boolean checkResponse) {
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.notificationRule);
        if (checkResponse) {
            checkResponse(response);
        }
        return response;
    }

    public Response notificationListRead() {
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.notification);
        checkResponse(response);
        return response;
    }

    public Response setEquipmentThresholdVPV(String equipmentId, int newMaxDistanceAlarmLevelMain, int newMaxDistanceAlarmLevelBlower, int newMaxDistanceAbortLevelMain, int newMaxDistanceAbortLevelBlower) {
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.equipmentAdmin);
        checkResponse(response);
        String newBody = changeThresholdVPVJSON(response.asString(), equipmentId, newMaxDistanceAlarmLevelMain, newMaxDistanceAlarmLevelBlower, newMaxDistanceAbortLevelMain, newMaxDistanceAbortLevelBlower);
        Response response1 = sendAmazonRequest(PUT.getValue(), AssembledUrls.equipmentAdmin, newBody);
        checkResponse(response1);

        return response1;
    }


    public Response setEquipmentThresholdGPV(String equipmentId, int newMaxDistanceAlarmLevel) {
        Response response = sendAmazonRequest(GET.getValue(), AssembledUrls.equipmentAdmin);
        checkResponse(response);
        String newBody = changeThresholdGPVJSON(response.asString(), equipmentId, newMaxDistanceAlarmLevel);
        Response response1 = sendAmazonRequest(PUT.getValue(), AssembledUrls.equipmentAdmin, newBody);
        checkResponse(response1);
        return response1;
    }

    public void checkEquipmentThresholdGPV() {
        //set current threshold to 'default'
        setEquipmentThresholdGPV(AssembledEquipments.equipmentGpv, 200);
        //set new thresold and compare with expected
        setEquipmentThresholdGPV(AssembledEquipments.equipmentGpv, 202);
        Response responseToCheck = sendAmazonRequest(GET.getValue(), AssembledUrls.equipmentAdmin);
        checkResponse(responseToCheck);
        checkThresoldGPVJSON(responseToCheck.asString(), AssembledEquipments.equipmentGpv, 202);
        //set threshold to 'default' value
        setEquipmentThresholdGPV(AssembledEquipments.equipmentGpv, 200);
    }

    public void checkEquipmentThresholdVPV() {
        //set current thresholds to 'default'
        setEquipmentThresholdVPV(AssembledEquipments.equipmentVpv, 90, 95, 120, 125);
        //set new thresolds and compare with expected
        setEquipmentThresholdVPV(AssembledEquipments.equipmentVpv, 91, 96, 121, 126);
        Response responseToCheck = sendAmazonRequest(GET.getValue(), AssembledUrls.equipmentAdmin);
        checkResponse(responseToCheck);
        checkThresoldVPVJSON(responseToCheck.asString(), AssembledEquipments.equipmentVpv, 91, 96, 121, 126);
        //set thresholds to 'default' values
        setEquipmentThresholdVPV(AssembledEquipments.equipmentVpv, 90, 95, 120, 125);
    }

    public void checkFrequencyLabelsVPV() {
        //get current equipment freq labels & set them to 'defaults'
        Response response1 = sendAmazonRequest(GET.getValue(), AssembledUrls.equipmentAdmin);
        checkResponse(response1);
        String defaultBody = changeFreqLabelVPVJSON(response1.asString(), AssembledEquipments.equipmentVpv, defaultMain, defaultBlower);
        Response response2 = sendAmazonRequest(PUT.getValue(), AssembledUrls.equipmentAdmin, defaultBody);
        checkResponse(response2);
        //set new freq labels
        String newBody = changeFreqLabelVPVJSON(response1.asString(), AssembledEquipments.equipmentVpv, newMain, newBlower);
        Response response3 = sendAmazonRequest(PUT.getValue(), AssembledUrls.equipmentAdmin, newBody);
        checkResponse(response3);
        //get new freq labels and compare with expected
        Response response4 = sendAmazonRequest(GET.getValue(), AssembledUrls.equipmentAdmin);
        checkResponse(response4);
        checkFreqLabelVPVJSON(response4.asString(), AssembledEquipments.equipmentVpv, newMain, newBlower);
        //set freq labels to 'default' values
        sendAmazonRequest(PUT.getValue(), AssembledUrls.equipmentAdmin, defaultBody);
    }

    public Response notificationRuleUpdate(String jsonBody) {
        Response response = sendAmazonRequest(PUT.getValue(), AssembledUrls.notificationRule, jsonBody);
        checkResponse(response);
        return response;
    }

    @Step("Deleting rule")
    public void notificationRuleDelete(String idOfNotificationRule) {
        JSONHandler jsonHandler = new JSONHandler();
        String jsonBody = jsonHandler.notificationRuleDeleteJSON(idOfNotificationRule);
        Response response = sendAmazonRequest(DELETE.getValue(), AssembledUrls.notificationRule, jsonBody);
        checkResponse(response);
    }

    @Step("Deleting notifications")
    public void notificationRuleDelete() {
        JSONHandler jsonHandler = new JSONHandler();
        String jsonBody = jsonHandler.notificationRuleDeleteJSON(idOfCreatedNotificationRule);
        Response response = sendAmazonRequest(DELETE.getValue(), AssembledUrls.notificationRule, jsonBody);
        checkResponse(response);
    }

    private void checkStatusCode(Response response) {
        Assert.assertTrue(response.statusCode() == 200 || response.statusCode() == 304 || response.statusCode() == 201 || response.statusCode() == 202
                || response.statusCode() == 203 || response.statusCode() == 204 || response.statusCode() == 205 || response.statusCode() == 206
                || response.statusCode() == 207 || response.statusCode() == 208 || response.statusCode() == 209);
    }

    private void checkStatusCodeSoft(Response response) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.statusCode() == 200 || response.statusCode() == 304 || response.statusCode() == 201 || response.statusCode() == 202
                || response.statusCode() == 203 || response.statusCode() == 204 || response.statusCode() == 205 || response.statusCode() == 206
                || response.statusCode() == 207 || response.statusCode() == 208 || response.statusCode() == 209);
    }

    private void checkErrorInResponseBody(Response response) {
        if (!response.asString().contains("error") && !response.asString().contains("exception") && !response.asString().contains("expired\"") && !response.asString().contains("timed out")) {
            Assert.assertTrue(true);
        } else {
            if (response.asString().contains("\"expired\":false") || response.asString().contains("\"expired\":true,\"accessKeyId\"") || response.asString().contains("Dashboards list is empty")|| response.asString().contains("No notifications to delete")) {
                Assert.assertTrue(true);
            } else {
                Assert.assertTrue(false);
                System.out.println("ERR: error message found in response body");
            }
        }
    }

    private void checkErrorInResponseBodySoft(Response response) {
        SoftAssert softAssert = new SoftAssert();
        if (!response.asString().contains("error") && !response.asString().contains("exception") && !response.asString().contains("expired") && !response.asString().contains("timed out")) {
            softAssert.assertTrue(true);
        } else {
            if (response.asString().contains("\"expired\":false") || response.asString().contains("\"expired\":true,\"accessKeyId\"") || response.asString().contains("Dashboards list is empty") || response.asString().contains("No notifications to delete")) {
                softAssert.assertTrue(true);
            } else {
                softAssert.assertTrue(false);
                System.out.println("ERR: error message found in response body");
            }
        }
    }

    @Step("Check if response contains error message in body or 4** 5** http status code")
    private void checkResponse(Response response) {
        checkStatusCode(response);
        checkErrorInResponseBody(response);
    }

    private void checkResponseSoft(Response response) {
        checkStatusCodeSoft(response);
        checkErrorInResponseBodySoft(response);
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void dashboardDeleteById(String id) {
        String jsonBody = new Dashboard().buildDeleteById(id);
        Response response = sendAmazonRequest(DELETE.getValue(), dashboard, jsonBody);
        checkResponse(response);
    }

    private Response dashboardsGet() {
        return sendAmazonRequest(GET.getValue(), dashboard);
    }

    @Step("Creating dashboard.")
    public void dashboardCreate(String body) {
        Response response = sendAmazonRequest(POST.getValue(), dashboard, body);
        checkResponse(response);
        idOfCreatedDashboard = getIdOfCreatedDashboard(response.asString());
    }

    @Step("Checking if dashboard is created")
    public void checkDashboardCreated() {
        Response response = dashboardsGet();
        checkResponse(response);
        Assert.assertTrue(response.asString().contains(idOfCreatedDashboard));
        Assert.assertTrue(response.asString().contains(thingVPVForDashboard) || response.asString().contains(thingGPVForDashboard));
    }

    public void dashboardUpdate(String body) {

    }

    public void checkDashboardUpdated(String updatedBody) {

    }

    @Step("Deleting created dashboard.")
    public void dashboardDelete() {
        dashboardDeleteById(idOfCreatedDashboard);
    }

    @Step("Cheking if dashboard is deleted.")
    public void checkDashboardDeleted() {
        Response response = dashboardsGet();
        checkResponse(response);
        Assert.assertFalse(response.asString().contains(idOfCreatedDashboard));
    }

    @Step("Check if GPV equipment data is present.")
    public void checkEquipmentDataGPV() {
        String url = AssembledUrls.chart + "/" + AssembledEquipments.equipmentGpv + "?startDate=" + (System.currentTimeMillis() - 60000000);
        Response response = sendAmazonRequest(GET.getValue(), url, false);
        checkResponse(response);
        checkChartData(response);
    }

    @Step("Check if VPV equipment data is present on all channels.")
    public void checkEquipmentDataVPV(String channel, String type) {
        String url = AssembledUrls.chart + "/" + AssembledEquipments.equipmentVpv + "?channelIdx=" + channel + "&startDate=" + (System.currentTimeMillis() - 60000000) + "&type=" + type;
        Response response = sendAmazonRequest(GET.getValue(), url, false);
        checkResponse(response);
        checkChartData(response);
    }

    @Step("Check if VPV equipment have data in the last 10 minutes on all channels")
    public void checkEquipmentNewDataVPV(String channel, String type) {
        String url = AssembledUrls.chart + "/" + AssembledEquipments.equipmentVpv + "?channelIdx=" + channel + "&startDate=" + (System.currentTimeMillis() - 600000) + "&type=" + type;
        Response response = sendAmazonRequest(GET.getValue(), url, false);
        checkResponse(response);
        checkChartData(response);
    }

    @Step("Check if GPV equipment have data in the last 10 minutes")
    public void checkEquipmentNewDataGPV() {
        String url = AssembledUrls.chart + "/" + AssembledEquipments.equipmentGpv + "?startDate=" + (System.currentTimeMillis() - 600000);
        Response response = sendAmazonRequest(GET.getValue(), url, false);
        checkResponse(response);
        checkChartData(response);
    }

    private void checkChartData(Response response) {
        Assert.assertTrue(
                response.asString().contains("{\"x\":") &&
                        response.asString().contains("result") &&
                        response.asString().contains("\"y\":")
        );
    }

    @Step("Check if GPV equipment is not deleted.")
    public void checkEquipmentNotDeletedGPV() {
        String url = AssembledUrls.equipment + "/" + AssembledEquipments.equipmentGpv;
        Response response = sendAmazonRequest(GET.getValue(), url);
        checkResponse(response);
        Assert.assertTrue(response.asString().contains("\"isDeleted\":0"));
    }

    @Step("Check if VPV equipment is not deleted")
    public void checkEquipmentNotDeletedVPV() {
        String url = AssembledUrls.equipment + "/" + AssembledEquipments.equipmentVpv;
        Response response = sendAmazonRequest(GET.getValue(), url);
        checkResponse(response);
        Assert.assertTrue(response.asString().contains("\"isDeleted\":0"));
    }

}
