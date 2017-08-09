package mechanics.ui.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class WaitsAsserts {

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void assertByTitle(String title) {
        WebDriver driver = WebDriverManager.getDriver();
        String currentTitle = driver.getTitle();
        assertEquals(currentTitle, title);
    }

    public static void assertContainsByTitle(String title) {
        WebDriver driver = WebDriverManager.getDriver();
        String currentTitle = driver.getTitle();
        assertTrue(currentTitle.contains(title));

    }

    public void assertTextNotEqualsByXpath(String xpath, String text) {
        WebDriver driver = WebDriverManager.getDriver();
        //driver.navigate().refresh();
        waitForVisibilityByXpath(xpath);
        String aIssueTitle = driver.findElement(By.xpath(xpath)).getText();
        assertFalse(aIssueTitle.contains(text));
    }

    public void assertTextByXpath(String xpath, String text) {
        WebDriver driver = WebDriverManager.getDriver();
        waitForVisibilityByXpath(xpath);
        String anElementTile = driver.findElement(By.xpath(xpath)).getText();
        assertTrue(anElementTile.contains(text));
    }

    public void assertXpathVisible(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        assertTrue(driver.findElement(By.xpath(xpath)).isDisplayed());
    }

    public void waitForVisibilityByXpath(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 20, 100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitForInvisibilityByXpath(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    public boolean elementExistsByXpath(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        boolean exists =  !driver.findElements(By.xpath(xpath)).isEmpty();
        driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
        return exists;
    }

    public int elementsCountByXpath(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        int elementCount = 0;
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        elementCount = driver.findElements(By.xpath(xpath)).size();
        driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
        return elementCount;
    }

    public void waitForElementRemovedByXpath(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        boolean exists = false;
        int counter = 0;
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        exists = driver.findElements(By.xpath(xpath)).size() != 0;
        driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
        while (exists && counter <= 10) {
            sleep(500);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            exists = driver.findElements(By.xpath(xpath)).size() != 0;
            driver.manage().timeouts().implicitlyWait(WebDriverFactory.webDriverImplicitlyWait, TimeUnit.SECONDS);
            counter++;
        }
    }

    public void waitForVisibilityByCSS(String css) {
        WebDriver driver = WebDriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
    }

    public void waitForClickableByXpath(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public void waitForClickableByXpathIgnoringStaleElementException(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.id(xpath)));
    }

    public void waitForClickableByCSS(String css) {
        WebDriver driver = WebDriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
    }

    public void assertTextByCSS(String css, String text) {
        WebDriver driver = WebDriverManager.getDriver();
        // driver.navigate().refresh();
        waitForVisibilityByXpath(css);
        String anElementTile = driver.findElement(By.cssSelector(css)).getText();
        assertTrue(anElementTile.contains(text));
    }

    public boolean retryingFindClick(WebDriver driver, By by) {
        Exception exception = null;
        boolean result = false;
        int attempts = 0;
        while (attempts <= 3) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
                exception = e;
            } catch (WebDriverException w) {
                exception = w;
            }
            attempts++;
        }
        if (!result) {
            exception.printStackTrace();
        }
        return result;
    }

    public boolean retryingFindClickByXpath(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        WebDriverException exception = null;
        boolean result = false;
        int attempts = 0;
        while (!result && attempts <= 5) {
            try {
                driver.findElement(By.xpath(xpath)).click();
                result = true;
                break;
            } catch (WebDriverException w) {
                exception = w;
            } finally {
                WaitsAsserts.sleep(200);
                attempts++;
            }
        }
        if (!result) {
            exception.printStackTrace();
        }
        return result;
    }

    public boolean retryingFindClickByXpath2(String xpath) {
        WebDriver driver = WebDriverManager.getDriver();
        boolean result = false;
        int attempts = 0;
        int failedAttempts = 0;
        while (!result && attempts <= 2) {
            try{
                driver.findElement(By.xpath(xpath)).click();
                result = true;
                break;
            }
            catch (WebDriverException e){
                result = false;
                failedAttempts++;
            }
            attempts++;
        }
        if (attempts!=0 || failedAttempts!=0 || !result) {
            System.out.println("Attempts: " + attempts);
            System.out.println("Failed attempts: " + failedAttempts);
            System.out.println("Result :" + result);
        }
        return result;
    }

    public boolean commonWaitingClick(String xpath) {
        new JSWaiter().waitUntilJSReady();
        waitForVisibilityByXpath(xpath);
        waitForClickableByXpath(xpath);
        return retryingFindClickByXpath2(xpath);
    }

    public boolean clickableWaitingClick(String xpath) {
        new JSWaiter().waitUntilJSReady();
        waitForClickableByXpath(xpath);
        return retryingFindClickByXpath2(xpath);
    }
}