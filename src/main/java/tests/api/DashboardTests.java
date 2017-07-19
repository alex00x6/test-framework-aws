package tests.api;

import mechanics.api.ListenerAPI;
import mechanics.api.RequestManagerAPI;
import mechanics.api.json.Dashboard;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.model.SeverityLevel;

/**
 * Created by user on 03.05.2017.
 */
@Listeners(ListenerAPI.class)
@Title("Dashboards test suite.")
public class DashboardTests {
    @Features("Dashboards")
    @Stories("[API][VPV] CRUD operations with Smart Sensor Dashboard (with zoomable charts, Canvas).")
    @Description("Create/Read/Update/Delete dashboard.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void DashboardCRUDVPVCanvas() {
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        requestManagerAPI.dashboardCreate(new Dashboard().buildVPVCanvas());
        requestManagerAPI.checkDashboardCreated();
        requestManagerAPI.dashboardDelete();
        requestManagerAPI.checkDashboardDeleted();
    }

    @Features("Dashboards")
    @Stories("[API][VPV] CRUD operations with Smart Sensor Dashboard(Kibana).")
    @Description("Create/Read/Update/Delete dashboard.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void dashboardCRUDVPVKibana() {
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        requestManagerAPI.dashboardCreate(new Dashboard().buildVPVKibana());
        requestManagerAPI.checkDashboardCreated();
        requestManagerAPI.dashboardDelete();
        requestManagerAPI.checkDashboardDeleted();
    }

    @Features("Dashboards")
    @Stories("[API][GPV] CRUD operations with General Vibrations chart - 0-255 range, Kibana.")
    @Description("Create/Read/Update/Delete dashboard.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void dashboardCRUDGPVKibana255() {
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        requestManagerAPI.dashboardCreate(new Dashboard().buildGPVKibana255());
        requestManagerAPI.checkDashboardCreated();
        requestManagerAPI.dashboardDelete();
        requestManagerAPI.checkDashboardDeleted();
    }

    @Features("Dashboards")
    @Stories("[API][GPV] CRUD operations withGeneral Vibrations chart - 0-25 range, Kibana.")
    @Description("Create/Read/Update/Delete dashboard.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void dashboardCRUDGPVKibana25() {
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        requestManagerAPI.dashboardCreate(new Dashboard().buildGPVKibana25());
        requestManagerAPI.checkDashboardCreated();
        requestManagerAPI.dashboardDelete();
        requestManagerAPI.checkDashboardDeleted();
    }

    @Features("Dashboards")
    @Stories("[API][GPV] CRUD operations withGeneral Vibrations chart - 0-25 range, Canvas.")
    @Description("Create/Read/Update/Delete dashboard.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void dashboardCRUDGPVCanvas25() {
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        requestManagerAPI.dashboardCreate(new Dashboard().buildGPVCanvas25());
        requestManagerAPI.checkDashboardCreated();
        requestManagerAPI.dashboardDelete();
        requestManagerAPI.checkDashboardDeleted();

    }

    @Features("Dashboards")
    @Stories("[API][GPV] CRUD operations withGeneral Vibrations chart - 0-255 range, Canvas.")
    @Description("Create/Read/Update/Delete dashboard.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void dashboardCRUDGPVCanvas255() {
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        requestManagerAPI.dashboardCreate(new Dashboard().buildGPVCanvas255());
        requestManagerAPI.checkDashboardCreated();
        requestManagerAPI.dashboardDelete();
        requestManagerAPI.checkDashboardDeleted();
    }
}
