package tests.ui;

import mechanics.ui.pageObjects.Dasboards.Dashboard;
import mechanics.ui.pageObjects.Dasboards.DashboardList;
import mechanics.ui.pageObjects.LogInPage;
import mechanics.ui.pageObjects.MainMenu;
import mechanics.ui.utils.ListenerUi;
import mechanics.ui.utils.WaitsAsserts;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex Storm on 19.06.2017.
 */
@Listeners(ListenerUi.class)
public class DashboardsCRUD {

    @BeforeClass
    public void before() {
        LogInPage log = new LogInPage();
        log.getToIoTPage();
        log.loginVsAmazonOrGoogle();
    }


    private void removeOverlayIfPresent() {
        String overlay = "//body[@style=\"overflow: hidden;\"]";
        String overlay2 = "//*[contains(@style, \"position: fixed; height: 100%; width: 100%; top: 0px; left: 0px; opacity: 1; background-color: rgba(0, 0, 0, 0.54); -webkit-tap-highlight-color: rgba(0, 0, 0, 0); will-change: opacity; transform: translateZ(0px); transition: left 0ms cubic-bezier(0.23, 1, 0.32, 1) 0ms, opacity 400ms cubic-bezier(0.23, 1, 0.32, 1) 0ms; z-index: 1400;\")]";
        WaitsAsserts waitsAsserts = new WaitsAsserts();
        if (waitsAsserts.elementExistsByXpath(overlay) || waitsAsserts.elementExistsByXpath(overlay2)) {
            waitsAsserts.waitForClickableByXpath("//*[@type=\"button\" and not(@id=\"flat_btn_handle\") and @tabindex=\"0\" and descendant::*[text()=\"Cancel\"]]");
            waitsAsserts.retryingFindClickByXpath("//*[@type=\"button\" and not(@id=\"flat_btn_handle\") and @tabindex=\"0\" and descendant::*[text()=\"Cancel\"]]");
        }
        WaitsAsserts.sleep(1500);
    }

    @Features("Dashboards")
    @Stories("[UI][GPV] CRUD scenario.")
    @Description("Creating/reading/updating/deleting Canvas 0,25 GPV dashboard.")
    @Test
    public void crudGPVCanvas25() throws InterruptedException {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful test dashboard " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateGPVCanvas25();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        //select second equip, which currently is a pump d11
        dashboardList.creationSelectGpvEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();
    }

    @Features("Dashboards")
    @Stories("[UI][GPV] CRUD scenario.")
    @Description("Creating/reading/updating/deleting Canvas 0,255 GPV dashboard.")
    @Test
    public void crudGPVCanvas255() throws InterruptedException {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful test dashboard " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateGPVCanvas255();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        //select second equip, which currently is a pump d11
        dashboardList.creationSelectGpvEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();
    }

    @Features("Dashboards")
    @Stories("[UI][GPV] CRUD scenario.")
    @Description("Creating/reading/updating/deleting Kibana 0,25 GPV dashboard.")
    @Test
    public void crudGPVKibana25() throws InterruptedException {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful test dashboard " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateGPVKibana25();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        //select second equip, which currently is a pump d11
        dashboardList.creationSelectGpvEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();
    }

    @Features("Dashboards")
    @Stories("[UI][GPV] CRUD scenario.")
    @Description("Creating/reading/updating/deleting Kibana 0,255 GPV dashboard.")
    @Test
    public void crudGPVKibana255() throws InterruptedException {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful test dashboard " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateGPVKibana255();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        //select second equip, which currently is a pump d11
        dashboardList.creationSelectGpvEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();
    }

    @Features("Dashboards")
    @Stories("[UI][VPV] CRUD scenario.")
    @Description("Creating/reading/updating/deleting Canvas with Zoomable charts VPV dashboard.")
    @Test
    public void crudVPVCanvas() throws InterruptedException {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful test dashboard " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateVPVCanvas();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        //select second equip, which currently is a pump d11
        dashboardList.creationSelectVpvEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();
    }

    @Features("Dashboards")
    @Stories("[UI][VPV] CRUD scenario.")
    @Description("Creating/reading/updating/deleting Canvas with Zoomable charts v1 VPV dashboard.")
    @Test
    public void crudVPVCanvasV1() throws InterruptedException {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful test dashboard " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateVPVCanvasV1();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        //select second equip, which currently is a pump d11
        dashboardList.creationSelectVpvEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();
    }

    @Features("Dashboards")
    @Stories("[UI][VPV] CRUD scenario.")
    @Description("Creating/reading/updating/deleting Kibana VPV dashboard.")
    @Test
    public void crudVPVKibana() throws InterruptedException {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful vpv kibana " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateVPVKibana();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        //select second equip, which currently is a pump d11
        dashboardList.creationSelectVpvEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();
    }

    @Features("Dashboards")
    @Stories("[UI][ICT] CRUD scenario.")
    @Description("Creating/reading/updating/deleting  ICT Tester Dashboard.")
    @Test
    public void crudICTTesterDashboard() {
        MainMenu mainMenu = new MainMenu();
        Dashboard dashboard = new Dashboard();
        DashboardList dashboardList = new DashboardList();
        int randInt = ThreadLocalRandom.current().nextInt(10, 999999 + 1);
        String dashName = "Wonderful test dashboard " + randInt;

        removeOverlayIfPresent();

        //click dashboard in main menu
        mainMenu.clickDashboardInMenu();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click floatingbutton at dashboard list
        dashboardList.clickFloatingButton();
        //click create vpv canvas dashboard
        dashboardList.creationClickCreateICTTesterDashboard();
        // /click on name field
        dashboardList.creationClickNameField();
        //type name into name input
        dashboardList.creationEnterName(dashName);
        //click continue
        dashboardList.creationClickContinue();
        //click green plus on table to add equipment
        dashboardList.creationAddEquipment();
        dashboardList.creationSelect1ICTEquip();
        //click add
        dashboardList.creationAddSelectedEquip();
        //click save button
        dashboardList.creationClickSave();
        //click cogwheel button at dashboard page
        dashboard.clickCogwheelButtonIfExists();
        //click 3 dot menu on first item in dashboards list
        dashboardList.clickDashboardMenu(dashName);
        //click delete in appeared menu
        dashboardList.clickDeleteInDashboardMenu();
        //click delete in popup window
        dashboardList.clickDeleteInPopUpWindow();

    }
}
