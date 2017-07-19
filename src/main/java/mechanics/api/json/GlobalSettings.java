package mechanics.api.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mechanics.system.constant.AssembledUrls;
import org.jglue.fluentjson.JsonBuilderFactory;

/**
 * Created by Alex Storm on 31.05.2017.
 */
public class GlobalSettings {
    private String adminReportUrl = AssembledUrls.skedlerEndpoint;
    private int OEEglobal = 0;
    private int acknowledged = 30;
    private int fredInterval = 0;
    private int triggered = 300;
    private String id = null;

    public String buildRequest() {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("OEEglobal", OEEglobal)
                .add("acknowledged", acknowledged)
                .add("adminReportUrl", adminReportUrl)
                .add("fredInterval", fredInterval)
                .add("triggered", triggered)
                .add("id", id)
                .getJson();
        return jsonObject.toString();
    }

    public void parseResponse(String jsonString) {
        JsonObject jsonObject = parseToJsonObject(jsonString);
        JsonObject global = jsonObject.getAsJsonObject("globalSettings");
        adminReportUrl = global.get("adminReportUrl").getAsString();
        OEEglobal = global.get("OEEglobal").getAsInt();
        acknowledged = global.get("acknowledged").getAsInt();
        fredInterval = global.get("fredInterval").getAsInt();
        triggered = global.get("triggered").getAsInt();
        id = global.get("id").getAsString();
    }


    private JsonObject parseToJsonObject(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
        return jsonObject;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminReportUrl() {
        return adminReportUrl;
    }

    public void setAdminReportUrl(String adminReportUrl) {
        this.adminReportUrl = adminReportUrl;
    }

    public int getOEEglobal() {
        return OEEglobal;
    }

    public void setOEEglobal(int OEEglobal) {
        this.OEEglobal = OEEglobal;
    }

    public int getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(int acknowledged) {
        this.acknowledged = acknowledged;
    }

    public int getFredInterval() {
        return fredInterval;
    }

    public void setFredInterval(int fredInterval) {
        this.fredInterval = fredInterval;
    }

    public int getTriggered() {
        return triggered;
    }

    public void setTriggered(int triggered) {
        this.triggered = triggered;
    }
}
