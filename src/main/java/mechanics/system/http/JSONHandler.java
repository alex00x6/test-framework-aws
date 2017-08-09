package mechanics.system.http;

import com.google.gson.JsonObject;
import mechanics.system.aws.SignAWSv4;
import mechanics.system.constant.AssembledEquipments;
import mechanics.system.credentials.RoleSwitcher;
import org.jglue.fluentjson.JsonBuilderFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.util.ArrayList;

public class JSONHandler extends SignAWSv4 {
    private String defaultDashboardVPVName = "someAutoTestNameVPV";
    private String defaultDashboardVPVDescription = "someAutoTestDescriptionVPV";
    private String defaultDashboardGPVName = "someAutoTestNameGPV";
    private String defaultDashboardGPVDescription = "someAutoTestDescriptionGPV";

    private String testEmail = RoleSwitcher.getCurrentUser().getEmail();
    private String thingVpv = AssembledEquipments.equipmentVpv;
    private String thingGpv = AssembledEquipments.equipmentGpv;

    public String getUserIdFromUserList(String jsonString, String email, String role){
        JSONArray array = parseToJSONArray(jsonString);
        JSONObject object;
        String id = null;
        for (Object anArray : array) {
            object = (JSONObject) anArray;
            if (object.get("email").equals(email) && object.get("group").equals(role)) {
                id = object.get("id").toString();
            }
        }
        return id;
    }


    public JsonObject reportCreate(String templateId, String equipment, String name) {
//        String jsonBody = "{\"templateId\":\"Vacuum-Pump-Vibration-Report---Optimized-for-Printing---Daily\",\"emaillist\":\"vasya.ossystem@gmasill.com\"
// ,\"filter\":\"equipmentId:"+thingGPV+"\",\"filter_name\":\"Vacuum-Pump-Vibration-Report-List---Optimized-for-Printing---Daily\",\"excelEnabled\":false}";

        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("templateId", templateId)
                .add("emaillist", testEmail)
                .add("filter", "equipmentId:" + equipment)
                .add("filter_name", name)
                .add("excelEnabled", false)
                .getJson();
        return jsonObject;
    }

    public JsonObject reportSendNow(String id, String templateId, String equipment, String name) {
        // "{\"filterId\":\""+idOfCreatedReport+"\",\"templateId\":\"GPV-Smart-Sensor-Report-15-minutes-activity-1\",\"emaillist\":\"geloksmmm@gmail.com,kov.ossystem@gmail.com\",
        // \"filter\":\"equipmentId:"+thingGPV+"\",\"filter_name\":\"GPV-Smart-Sensor-Report-List-15-minutes\",\"excelEnabled\":false}";

        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("filterId", id)
                .add("templateId", templateId)
                .add("emaillist", testEmail)
                .add("filter", "equipmentId:" + equipment)
                .add("filter_name", name)
                .add("excelEnabled", false)
                .getJson();
        return jsonObject;
    }

    public JsonObject reportDelete(String filter_name, String filterId, String userId) {
        //{"filter_name":"Vacuum-Pump-Vibration-Report-List---Optimized-for-Printing---Weekly",
        // "filterTitle":"Vacuum-Pump-Vibration-Report-List---Optimized-for-Printing---Weekly kov.ossystem@gmail.com",
        // "equipments":"Thing-000011-i1",
        // "id":null,
        // "filterId":"173ba9ae-6abb-40fa-b194-479403c58b4c",
        // "emails":"kov.ossystem@gmail.com",
        // "userId":"bbc046ce-daff-4223-a144-ae453ea8a32b",
        // "createdAt":null,
        // "excelIncluded":null}
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("filter_name", filter_name)
                //  .add("filterTitle", filterTitle)
                //  .add("equipments", equipments)
                //  .add("id", "")
                .add("filterId", filterId)
                //   .add("emails", testEmail)
                .add("userId", userId)
                //   .add("createdAt", "")
                //   .add("excelIncluded", "")
                .getJson();
        return jsonObject;
    }

    public String getIdOfCreatedNotificationRule(String response) {
        JSONObject jsonObject = parseToJSONObject(response);
        String id = jsonObject.get("id").toString();
        return id;
    }

    public String getIdOfCreatedDashboard(String response) {
        JSONObject jsonObject = parseToJSONObject(response);
        JSONObject item = (JSONObject) jsonObject.get("item");
        String id = item.get("id").toString();
        return id;
    }

    public String getIdOfCreatedReport(String response) {
        JSONObject jsonObject = parseToJSONObject(response);
        String filterId = jsonObject.get("filterId").toString();
        return filterId;
    }

    public String getIdOfUser(String response) {
        JSONObject jsonObject = parseToJSONObject(response);
        String userId = jsonObject.get("userId").toString();
        return userId;
    }

    public String getFilter_name(String response) {
        JSONObject jsonObject = parseToJSONObject(response);
        String filter_name = jsonObject.get("filter_name").toString();
        return filter_name;
    }


    public ArrayList<String> getIdsOfAllNotifications(String response) {
        JSONArray jsonArray = parseToJSONArray(response);
        ArrayList<String> ids = new ArrayList<>();
        for (Object aJsonArray : jsonArray) {
            JSONObject jsonObject = parseToJSONObject(aJsonArray.toString());
            String id = jsonObject.get("id").toString();
            ids.add(id);
        }
        return ids;
    }

    public void checkThresoldVPVJSON(String response, String equipmentId, int expectedAlarmMain, int expectedAlarmBlower, int expectedAbortMain, int expectedAbortBlower) {
        JSONObject object = getEquipmentObjectFromArray(response, equipmentId);
        String result;
        JSONArray array1 = (JSONArray) object.get("channels");
        for (int i1 = 0; i1 <= array1.size() - 1; i1++) {
            if (array1.get(i1).toString().contains("maxDistanceAlarmLevel") && array1.get(i1).toString().contains("maxDistanceAbortLevel")) {
                JSONObject obj = (JSONObject) array1.get(i1);
                if (obj.get("id").toString().equals("1")) {
                    int alarmMain = Integer.parseInt(obj.get("maxDistanceAlarmLevel").toString());
                    int abortMain = Integer.parseInt(obj.get("maxDistanceAbortLevel").toString());
                    Assert.assertTrue(alarmMain == expectedAlarmMain);
                    Assert.assertTrue(abortMain == expectedAbortMain);
                }
                if (obj.get("id").toString().equals("2")) {
                    int alarmBlower = Integer.parseInt(obj.get("maxDistanceAlarmLevel").toString());
                    int abortBlower = Integer.parseInt(obj.get("maxDistanceAbortLevel").toString());
                    Assert.assertTrue(alarmBlower == expectedAlarmBlower);
                    Assert.assertTrue(abortBlower == expectedAbortBlower);

                }
            }
        }
    }

    //костыль, переписать полностью, если нужны будут изменения
    public String changeThresholdVPVJSON(String response, String equipmentId, int newMaxDistanceAlarmLevelMain, int newMaxDistanceAlarmLevelBlower, int newMaxDistanceAbortLevelMain, int newMaxDistanceAbortLevelBlower) {
        JSONObject object = getEquipmentObjectFromArray(response, equipmentId);
        String result;
        JSONArray array1 = (JSONArray) object.get("channels");
        for (int i1 = 0; i1 <= array1.size() - 1; i1++) {
            if (array1.get(i1).toString().contains("maxDistanceAlarmLevel") && array1.get(i1).toString().contains("maxDistanceAbortLevel")) {
                JSONObject obj = (JSONObject) array1.get(i1);
                if (obj.get("id").toString().equals("1")) {
                    obj.replace("maxDistanceAlarmLevel", newMaxDistanceAlarmLevelMain);
                    obj.replace("maxDistanceAbortLevel", newMaxDistanceAbortLevelMain);
                    array1.set(i1, obj);
                }
                if (obj.get("id").toString().equals("2")) {

                    obj.replace("maxDistanceAlarmLevel", newMaxDistanceAlarmLevelBlower);
                    obj.replace("maxDistanceAbortLevel", newMaxDistanceAbortLevelBlower);
                    array1.set(i1, obj);
                }
            }
        }
        object.replace("channels", array1);

        result = "{\"items\": [" + object.toJSONString() + "]}";

        return result;
    }

    public String changeFreqLabelVPVJSON(String response, String equipmentId, String freqNamesMain, String freqNamesBlower) {
        JSONObject object = getEquipmentObjectFromArray(response, equipmentId);
        String result;

        JSONArray array1 = (JSONArray) object.get("channels");
        for (int i1 = 0; i1 <= array1.size() - 1; i1++) {
            if (array1.get(i1).toString().contains("maxDistanceAlarmLevel") && array1.get(i1).toString().contains("maxDistanceAbortLevel")) {
                JSONObject obj = (JSONObject) array1.get(i1);
                if (obj.get("id").toString().equals("1")) {
                    JSONArray array2 = (JSONArray) obj.get("freqs");
                    for (int i2 = 0; i2 <= array2.size() - 1; i2++) {
                        if (array2.get(i2).toString().contains("name")) {
                            JSONObject object1 = (JSONObject) array2.get(i2);
                            object1.replace("name", freqNamesMain);
                            array2.set(i2, object1);
                        }
                    }
                    array1.set(i1, obj);
                }
                if (obj.get("id").toString().equals("2")) {
                    JSONArray array2 = (JSONArray) obj.get("freqs");
                    for (int i2 = 0; i2 <= array2.size() - 1; i2++) {
                        if (array2.get(i2).toString().contains("name")) {
                            JSONObject object1 = (JSONObject) array2.get(i2);
                            object1.replace("name", freqNamesBlower);
                            array2.set(i2, object1);
                        }
                    }
                    array1.set(i1, obj);
                }
            }
        }
        object.replace("channels", array1);

        result = "{\"items\": [" + object.toJSONString() + "]}";

        return result;
    }

    public void checkFreqLabelVPVJSON(String response, String equipmentId, String freqNamesMain, String freqNamesBlower) {
        JSONObject object = getEquipmentObjectFromArray(response, equipmentId);

        JSONArray array1 = (JSONArray) object.get("channels");
        for (int i1 = 0; i1 <= array1.size() - 1; i1++) {
            if (array1.get(i1).toString().contains("maxDistanceAlarmLevel") && array1.get(i1).toString().contains("maxDistanceAbortLevel")) {
                JSONObject obj = (JSONObject) array1.get(i1);
                if (obj.get("id").toString().equals("1")) {
                    JSONArray array2 = (JSONArray) obj.get("freqs");
                    for (int i2 = 0; i2 <= array2.size() - 1; i2++) {
                        if (array2.get(i2).toString().contains("name")) {
                            JSONObject object1 = (JSONObject) array2.get(i2);
                            Assert.assertTrue(object1.get("name").toString().equals(freqNamesMain));
                        }
                    }
                }
                if (obj.get("id").toString().equals("2")) {
                    JSONArray array2 = (JSONArray) obj.get("freqs");
                    for (int i2 = 0; i2 <= array2.size() - 1; i2++) {
                        if (array2.get(i2).toString().contains("name")) {
                            JSONObject object1 = (JSONObject) array2.get(i2);
                            Assert.assertTrue(object1.get("name").toString().equals(freqNamesBlower));
                        }
                    }
                }
            }
        }
    }


    private JSONObject getEquipmentObjectFromArray(String jsonString, String equipmentId) {
        JSONArray array = parseToJSONArray(jsonString);
        JSONObject temp;
        JSONObject result = null;
        boolean found = false;

        for (int i = 0; i <= array.size() - 1; i++) {
            if (!found) {
                temp = (JSONObject) array.get(i);
                if (temp.containsValue(equipmentId)) {
                    result = temp;
                }
            }
        }
        return result;
    }

    public String changeThresholdGPVJSON(String response, String equipmentId, int newMaxDistanceAlarmLevel) {
        JSONObject object = getEquipmentObjectFromArray(response, equipmentId);
        String result = null;
        if (object.containsValue(equipmentId) && object.containsKey("maxDistanceAlarmLevel")) {
            object.replace("maxDistanceAlarmLevel", newMaxDistanceAlarmLevel);
            result = "{\"items\": [" + object.toJSONString() + "]}";
        }
        return result;
    }

    public void checkThresoldGPVJSON(String jsonString, String equipmentId, int expectedAlarm) {
        JSONObject object = getEquipmentObjectFromArray(jsonString, equipmentId);
        if (object.containsValue(equipmentId) && object.containsKey("maxDistanceAlarmLevel")) {
            int alarm = Integer.parseInt(object.get("maxDistanceAlarmLevel").toString());
            Assert.assertTrue(alarm == expectedAlarm);
        }
    }

    private JSONArray parseToJSONArray(String jsonString) {
        JSONParser parser = new JSONParser();
        JSONArray jsonObject = null;
        try {
            jsonObject = (JSONArray) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private JSONObject parseToJSONObject(String jsonString) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String notificationListAcknowledgeAllJSON(ArrayList<String> arrayList) {
        String jsonString = "{\"items\":[";
        for (int i = 0; i < arrayList.size(); i++) {
            jsonString = jsonString + "{\"id\":\"" + arrayList.get(i) + "\",\"status\":1}";
            if (arrayList.size() > i + 1) {
                jsonString = jsonString + ",";

            }
        }
        return jsonString + "]}";
    }

    public String notificationListDeleteAllJSON(ArrayList<String> arrayList) {
        String jsonString = "{\"items\":[";
        for (int i = 0; i < arrayList.size(); i++) {
            jsonString = jsonString + "{\"id\":\"" + arrayList.get(i) + "\"}";
            if (arrayList.size() > i + 1) {
                jsonString = jsonString + ",";
            }
        }
        return jsonString + "]}";
    }

    public String notificationRuleCreateJSONDefault() {
        String name = "Abnormal auto-test rule";
        String description = "some description";
        int type = 0;
        int channel = 0;
        String equipment = "Thing-90094-0";
        int threshold = 0;
        String trigger = "";
        String operation = ">=";
        int value = 0;
        int period = 0;
        int sensor = 1;
        return notificationRuleCreateJSON(name, description, type, channel, equipment, threshold, trigger, operation, value, period, sensor).toString();
    }


    public JsonObject notificationRuleCreateJSON(String name, String description, int type, int channel, String equipment, int threshold, String trigger, String operation, int value, int period, int sensor) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("active", true)
                .add("name", name)
                .add("description", description)
                .add("notificationType", 0)
                .add("type", type)
                .addArray("phones")
                .addObject().add("value", "+380634953177").add("name", "My Lifecell").end().end()
                .addArray("emails")
                .addObject().add("value", testEmail).add("name", "hhhhhhhhhhhhh").end().end()
                .addObject("notifications").add("alwaysSend", false).add("triggered", 10).add("acknowledged", 0).add("sms", false).add("emails", true).end()
                .addArray("equipmentIds").add(equipment).end()
                .add("channel", channel)
                .add("frq", 0)
                .add("threshold", threshold)
                .add("trigger", trigger)
                .add("operation", operation)
                .add("value", value)
                .add("period", period)
                .add("sensor", sensor).getJson();
        return jsonObject;
    }

    public JsonObject notificationRuleCreateJSON(String name, String description, int type, String channel, String equipment, String threshold, String trigger, String operation, int value, int period, int sensor) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("active", true)
                .add("name", name)
                .add("description", description)
                .add("notificationType", 0)
                .add("type", type)
                .addArray("phones")
                .addObject().add("value", "+380634953177").add("name", "My Lifecell").end().end()
                .addArray("emails")
                .addObject().add("value", testEmail).add("name", "hhhhhhhhhhhhh").end().end()
                .addObject("notifications").add("alwaysSend", false).add("triggered", 10).add("acknowledged", 0).add("sms", false).add("emails", true).end()
                .addArray("equipmentIds").add(equipment).end()
                .add("channel", channel)
                .add("frq", 0)
                .add("threshold", threshold)
                .add("trigger", trigger)
                .add("operation", operation)
                .add("value", value)
                .add("period", period)
                .add("sensor", sensor).getJson();
        return jsonObject;
    }


    public String notificationRuleUpdateJSON(String id) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addArray("items")
                .addObject()
                .add("id", id)
                .add("active", 1)
                .add("name", "Abnormal auto-test rule")
                .add("description", "some description")
                .add("notificationType", 0)
                .add("type", 0)
                .addArray("phones").addObject().add("value", "+380634953177").add("name", "My Lifecell").end().end()
                .addArray("emails").addObject().add("value", testEmail).add("name", "hhhhhhhhhhhhh").end().end()
                .addObject("notifications").add("alwaysSend", false).add("triggered", 10).add("acknowledged", 15).add("globalSettings", 0).add("sms", false).add("emails", false).end()
                .addArray("equipmentIds").add("Thing-090011-0").end()
                .add("channel", 0)
                .add("frq", 0)
                .add("threshold", 0)
                .add("trigger", "")
                .add("operation", ">=")
                .add("value", 30)
                .add("period", 0).add("sensor", 1).getJson();
        return jsonObject.toString();
    }

    public String notificationRuleDeleteJSON(String id) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addArray("items").addObject().add("id", id).getJson();
        return jsonObject.toString();
    }

    public String dashboardCreateCanvasVPVJSONDefault() {
        String thingVPV1 = thingVpv;
        String name = defaultDashboardVPVName;
        String description = defaultDashboardVPVDescription;

        return dashboardCreateCanvasVPVJSON(thingVPV1, name, description).toString();
    }

    private JsonObject dashboardCreateCanvasVPVJSON(String thingVPV1, String name, String description) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("type", 7)
                .addArray("equipmentIds").add(thingVPV1).end()
                .add("name", name)
                .add("description", description).getJson();
        return jsonObject;
    }

    public String dashboardCreateCanvasGPVJSONDefault() {
        String thingGPV = thingGpv;
        String name = defaultDashboardGPVName;
        String description = defaultDashboardGPVDescription;

        return dashboardCreateCanvasGPVJSON(thingGPV, name, description).toString();
    }

    public String dashboardCreateCanvasGPVJSONDefault(String equipmentId, String description) {
        String name = defaultDashboardGPVName;

        return dashboardCreateCanvasGPVJSON(equipmentId, name, description).toString();
    }

    private JsonObject dashboardCreateCanvasGPVJSON(String thingGPV, String name, String description) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("type", 9)
                .addArray("equipmentIds").add(thingGPV).end()
                .add("name", name).add("description", description).getJson();
        return jsonObject;
    }


    public String dashboardDeleteJSON(String id) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addArray("items").addObject().add("id", id).getJson();
        return jsonObject.toString();
    }

    public String equipmentOnOff(String id, Boolean active) {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addArray("items").addObject().add("id", id).add("isDataTransfer", active).getJson();

        return jsonObject.toString();
    }

    protected String getYieldInPeriod(String json) {
        JSONObject jsonObject = (JSONObject) parseToJSONObject(json).get("result");
        return jsonObject.get("yieldInPeriod").toString();
    }

    protected String getLastYieldPoint(String json){
        JSONObject jsonObject = (JSONObject) parseToJSONObject(json).get("result");
        JSONArray yield = (JSONArray) jsonObject.get("yield");
        JSONObject lastYield = (JSONObject) yield.get(yield.size()-1);
        return lastYield.get("y").toString();
    }
}
