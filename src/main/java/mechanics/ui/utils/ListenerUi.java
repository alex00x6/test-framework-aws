package mechanics.ui.utils;

import mechanics.api.RequestManagerAPI;
import mechanics.system.jar.Args;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class ListenerUi implements ITestListener {
    UiUtils utils = new UiUtils();
    private String params;

    private File captureScreenshot(WebDriver driver) {
        File file = null;
        try {
            file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            screenshotToAllure(file);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] screenshotToAllure(File screen) {
        byte[] screenShot = new byte[0];
        try {
            screenShot = Files.readAllBytes(screen.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenShot;
    }


    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("============Test started============");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        System.out.println("============Test passed successful============");
        String pathSucceed = "target/screenshots/" + params + "/success/" + tr.getMethod().getMethodName() + ".png";
        File screen = captureScreenshot(WebDriverManager.getDriver());
        try {
            FileUtils.copyFile(screen, new File(pathSucceed));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screenshot captured for test case:" + tr.getMethod().getMethodName());
    }


    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("============Test failed============");
        String pathFailed = "target/screenshots/" + params + "/failed/" + tr.getMethod().getMethodName() + ".png";
        System.out.println("============Log on fail============");
        WebDriver driver = WebDriverManager.getDriver();
        for (LogEntry entry : driver.manage().logs().get(LogType.PERFORMANCE).filter(Level.WARNING)) {
            System.out.println(entry.toString());
        }
        for (LogEntry entry : driver.manage().logs().get(LogType.BROWSER).filter(Level.WARNING)) {
            System.out.println(entry.toString());
        }
        System.out.println("============Log on fail============");


        File screen = captureScreenshot(WebDriverManager.getDriver());
        try {
            FileUtils.copyFile(screen, new File(pathFailed));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Screenshot captured for test case:" + tr.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("============Test skipped============");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("======Test failed, but that the expected result=======");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("============Starting testing process============");
//        String browserName = iTestContext.getCurrentXmlTest().getParameter("browserName");
//        String useGrid = iTestContext.getCurrentXmlTest().getParameter("useGrid");
//        Boolean boo = useGrid.contentEquals("true");

        String browserName = Args.browser;
        Boolean useGrid = Args.grid;
        WebDriver driver = WebDriverFactory.createInstance(browserName, useGrid);
        WebDriverManager.setWebDriver(driver);

        UiUtils util = new UiUtils();
        String currentDate = util.getTime();
        params = currentDate + "-" + browserName + "-grid-" + useGrid;
        RequestManagerAPI requestManager = new RequestManagerAPI();
        requestManager.setUpBaseApiGateway();
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("==========Finishing testing process ============");
        WebDriver driver = WebDriverManager.getDriver();

        WaitsAsserts waits = new WaitsAsserts();
        waits.sleep(5000);
        if (driver != null) {
            driver.quit();
        }

    }
}