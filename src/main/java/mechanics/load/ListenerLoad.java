package mechanics.load;

import mechanics.system.http.RequestSender;
import mechanics.system.jar.Args;
import mechanics.ui.pageObjects.LogInPage;
import mechanics.ui.utils.WaitsAsserts;
import mechanics.ui.utils.WebDriverFactory;
import mechanics.ui.utils.WebDriverManager;
import org.apache.commons.io.output.TeeOutputStream;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ListenerLoad implements ITestListener {
    private FileOutputStream fos = null;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        //gatlingInfoPrintUserStart();
        RequestSender.checkExpired();
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintUserEnd();
    }


    @Override
    public void onTestFailure(ITestResult tr) {
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintUserEnd();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintUserEnd();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintUserEnd();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        //for browser launch
//        String browserName = iTestContext.getCurrentXmlTest().getParameter("browserName");
//        String useGrid = iTestContext.getCurrentXmlTest().getParameter("useGrid");
//        Boolean boo = useGrid.contentEquals("true");
        String browserName = Args.browser;
        Boolean boo = Args.grid;
        WebDriver driver = WebDriverFactory.createInstance(browserName, boo);
        WebDriverManager.setWebDriver(driver);

        getCreds();

        try {
            fos = new FileOutputStream("loadReports/simulation.log");
            TeeOutputStream myOut = new TeeOutputStream(System.out, fos);
            PrintStream ps = new PrintStream(myOut);
            System.setOut(ps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        RequestSender.setStartDate();
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintTestStart();
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        //for browser
        WebDriver driver = WebDriverManager.getDriver();
        WaitsAsserts.sleep(5000);
        if (driver != null) {
            driver.quit();
        }


        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCreds() {
        LogInPage log = new LogInPage();
        log.getToIoTPageLoad();
        log.loginVsAmazonOrGoogle();
        log.getRequestSigns();
        RequestManagerLoad requestManagerLoad = new RequestManagerLoad();
//        requestManagerLoad.writeCredsTofile();
        requestManagerLoad.setUpBaseApiGateway();
    }

}