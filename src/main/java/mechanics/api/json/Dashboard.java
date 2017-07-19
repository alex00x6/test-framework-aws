package mechanics.api.json;

import com.google.gson.JsonObject;
import mechanics.system.constant.AssembledEquipments;
import org.jglue.fluentjson.JsonBuilderFactory;

/**
 * Created by Alex Storm on 02.06.2017.
 */
public class Dashboard {
    private int dashboardType;
    private String equipmentId;
    private String name = "Automatically Created Dashboard";
    private String description = "You should not see this one";
    private String idOfCreatedDashboard;

    public String buildRequest() {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .add("type", dashboardType)
                .addArray("equipmentIds").add(equipmentId).end()
                .add("name", name).add("description", description).getJson();
        return jsonObject.toString();
    }

    public String buildDeleteRequest() {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addArray("items").addObject().add("id", idOfCreatedDashboard).getJson();
        return jsonObject.toString();
    }

    public String parseResponse() {
        return null;
    }


    public String buildDeleteById(String id) {
        idOfCreatedDashboard = id;
        return buildDeleteRequest();
    }

    public String buildVPVCanvas() {
        dashboardType = 7;
        equipmentId = AssembledEquipments.equipmentVpv;
        return buildRequest();
    }

    public String buildVPVKibana() {
        dashboardType = 6;
        equipmentId = AssembledEquipments.equipmentVpv;
        return buildRequest();
    }

    public String buildGPVKibana25() {
        dashboardType = 4;
        equipmentId = AssembledEquipments.equipmentGpv;
        return buildRequest();
    }

    public String buildGPVKibana255() {
        dashboardType = 5;
        equipmentId = AssembledEquipments.equipmentGpv;
        return buildRequest();
    }

    public String buildGPVCanvas25() {
        dashboardType = 8;
        equipmentId = AssembledEquipments.equipmentGpv;
        return buildRequest();
    }

    public String buildGPVCanvas255() {
        dashboardType = 9;
        equipmentId = AssembledEquipments.equipmentGpv;
        return buildRequest();
    }

    public int getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(int dashboardType) {
        this.dashboardType = dashboardType;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
