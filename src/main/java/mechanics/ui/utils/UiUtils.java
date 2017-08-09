package mechanics.ui.utils;

import mechanics.system.jar.Args;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import ru.yandex.qatools.allure.annotations.Step;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static mechanics.ui.utils.WaitsAsserts.sleep;

public class UiUtils {

    private String ddMenuButtonXpath = "//*[@id=\"table_menu_btn\"]";
    private String ddMenuWindowCss = "body > div:nth-child(14) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)";

    public void makeScreenshot(WebDriver driver, String name) {
        sleep(1000);
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File("/target/screenshots/" + getTime() + "/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scrollPageUp(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -250);");
    }

    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("(dd.MM.yyyy) HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean checkActiveCSS(WebDriver driver, String css) {
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        boolean exists = !driver.findElements(By.cssSelector(css)).isEmpty();
        //System.out.println(exists + " <====> " + css);
        driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
        return exists;
    }


    public void changeFocusFF() {
        WaitsAsserts waitsAsserts = new WaitsAsserts();
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
        sleep(500);
    }

    //check expected condition (true/false) and if css presented or not click xpath or not
    public void checkDefaultItem(WebDriver driver, String cssActive, String cssButton, boolean expectedStatus) {
        WaitsAsserts waitsAsserts = new WaitsAsserts();
        //String menuButton = "//*[@id=\"table_menu_btn\"]";
        sleep(700);
        if (isDDMenuOpen(driver)) {
            System.out.println("Menu is open, performing check");
        } else {
            System.out.println("Menu is closed, opening...");
        }
        if (checkActiveCSS(driver, cssActive) != expectedStatus) {
            System.out.println("Status is not as expected, clicking on: " + cssButton);
            driver.findElement(By.cssSelector(cssButton)).click();
            sleep(700);
        } else {
            System.out.println("Status corresponds to expected, doing nothing :" + cssButton);
        }
    }

    public void checkDefaultItemFF(WebDriver driver, String cssActive, String cssButton, boolean expectedStatus) {
        WaitsAsserts waitsAsserts = new WaitsAsserts();
        //String menuButton = "//*[@id=\"table_menu_btn\"]";
        //String menu = "body > div:nth-child(14) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)";
        sleep(700);
        if (isDDMenuOpenFF(driver)) {
            System.out.println("Menu is open, performing check");
        } else {
            driver.findElement(By.xpath(ddMenuButtonXpath)).click();
            System.out.println("Menu is closed, opening...");
        }
        if (checkActiveCSS(driver, cssActive) != expectedStatus) {
            System.out.println("Status is not as expected, clicking on: " + cssButton);
            driver.findElement(By.cssSelector(cssButton)).click();
            sleep(700);
        } else {
            System.out.println("Status corresponds to expected, doing nothing :" + cssButton);
        }
    }

    public boolean isDDMenuOpen(WebDriver driver) {
        return !isElementClickableLong(driver, ddMenuButtonXpath);
    }

    public boolean isDDMenuOpenFF(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(700, TimeUnit.MILLISECONDS);
        if (!driver.findElements(By.cssSelector(ddMenuWindowCss)).isEmpty()) {
            driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
            return true;
        } else {
            driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
            return false;
        }

    }

    public boolean isElementClickable(WebDriver driver, String xpath) {
        driver.manage().timeouts().implicitlyWait(700, TimeUnit.MILLISECONDS);
        WebElement imgElement = driver.findElement(By.xpath(xpath));
        try {
            imgElement.click();
            driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
            return false;
        }
    }

    public boolean isElementClickableLong(WebDriver driver, String xpath) {
        WebElement imgElement = driver.findElement(By.xpath(xpath));
        try {
            imgElement.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public void recreateWebdriver() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (WebDriverManager.getDriver() != null) {
            WebDriver driver = WebDriverManager.getDriver();
            driver.quit();
            String browserName = Args.browser;
            Boolean useGrid = Args.grid;
            driver = WebDriverFactory.createInstance(browserName, useGrid);
            WebDriverManager.setWebDriver(driver);
            WaitsAsserts.sleep(2500);
        }
    }


}
