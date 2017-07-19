package tests.load;

import mechanics.load.GatlingReportAdapter;
import mechanics.load.ListenerLoad;
import mechanics.load.RequestManagerLoad;
import mechanics.load.ThreadLaunchDelayer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(ListenerLoad.class)
public class LoadChartsRefreshingGPVBurst {
    private final int threads = 100;
    private final int newThreadLaunchDelayMs = 5;//default 5000
    private final int tenMinutesCyclesCount = 1;

    @BeforeClass
    public void createDashboardGPV() {
        RequestManagerLoad requestManagerLoad = new RequestManagerLoad();
        requestManagerLoad.dashboardCreateCanvasGPV();
    }

    @AfterClass
    public void deleteDashboardGPV() {
        RequestManagerLoad requestManagerLoad = new RequestManagerLoad();
        requestManagerLoad.dashboardDeleteCanvasGPV();
    }

    @Test(threadPoolSize = threads, invocationCount = threads)
    public void refreshDashboardGPVBurst() {
        ThreadLaunchDelayer.delay(newThreadLaunchDelayMs);
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintUserStart();
        RequestManagerLoad requestManagerLoad = new RequestManagerLoad();
        requestManagerLoad.canvasGPVDashboardRefreshCycleBurst(tenMinutesCyclesCount);
    }
}
