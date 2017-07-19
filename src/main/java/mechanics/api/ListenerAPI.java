package mechanics.api;

import mechanics.system.credentials.RoleSwitcher;
import mechanics.system.http.RequestSender;
import mechanics.ui.pageObjects.LogInPage;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Created by user on 20.04.2017.
 */
public class ListenerAPI implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        RequestSender.checkExpired();
        System.out.println("Starting method: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        printOnFinish(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        printOnFinish(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        printOnFinish(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        printOnFinish(result);
    }

    @Override
    public void onStart(ITestContext context) {
        //for browser launch
        if (RoleSwitcher.getCurrentUser().getAwsKeys().getAccessKeyId() == null || RoleSwitcher.getCurrentUser().getCredentialsReceiveTime() < (System.currentTimeMillis() - 540000)) {
//            String browserName = Args.browser;
//            Boolean useGrid = Args.grid;
//            WebDriver driver = WebDriverFactory.createInstance(browserName, useGrid);
//            WebDriverManager.setWebDriver(driver);
//            getCreds();
//            RequestSender.setStartDate();
//            RequestSender.setCurrentUserRole(RoleSwitcher.getCurrentRoleName());
        }

        RequestManagerAPI requestManager = new RequestManagerAPI();
        requestManager.setUpBaseApiGateway();
    }

    @Override
    public void onFinish(ITestContext context) {
        //for browser
//        WebDriver driver = WebDriverManager.getDriver();
//        WaitsAsserts.sleep(5000);
//        if (driver != null) {
//            driver.quit();
//        }
    }

    private void getCreds() {
        LogInPage log = new LogInPage();
        log.getToIoTPage();
        log.loginVsAmazonOrGoogle();
        log.getRequestSigns();
        RequestManagerAPI requestManager = new RequestManagerAPI();
        requestManager.setUpBaseApiGateway();
    }

    private void printOnFinish(ITestResult result) {
        String resultName;
        switch (result.getStatus()) {
            case 1:
                resultName = "SUCCESS";
                break;
            case 2:
                resultName = "FAILURE";
                break;
            case 3:
                resultName = "SKIPPED";
                break;
            case 4:
                resultName = "SUCCESS % FAILURE";
                break;
            case 16:
                resultName = "STARTED";
                break;
            default:
                resultName = "";
                break;
        }

        System.out.println("Finished method: " + result.getMethod().getMethodName() + " Result: " + resultName);
        System.out.println("==================================");
    }

}
