package mechanics.ui.utils;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class WebDriverFactory {
    public static final int webDriverImplicitlyWait = 20;
    private static final String unpackedPrefix = "selenium/";
    private static final String windowsPathPrefix = "src/main/resources/selenium/windows/";
    private static final String linuxPathPrefix = "src/main/resources/selenium/linux/";

    public static WebDriver createInstance(String browserName, Boolean useGrid) {
        URL hostURL = null;
        try {
            hostURL = new URL("http://vzhost4:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capability = null;
        WebDriver driver = null;

        DesiredCapabilities cap = DesiredCapabilities.chrome();

//        this is for chrome headless mode, but headless mode will be supported only in chrome 60 on windows OS
//        final ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("enable-automation");
//        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("--disable-gpu");
//        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.WARNING);
        logPrefs.enable(LogType.PERFORMANCE, Level.WARNING);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        if (useGrid) {
            if (browserName.toLowerCase().contains("firefox")) {
                capability = DesiredCapabilities.firefox();
                capability.setBrowserName("firefox");
                capability.setPlatform(Platform.LINUX);
            }
            if (browserName.toLowerCase().contains("ie")) {
                capability = DesiredCapabilities.internetExplorer();
            }
            if (browserName.toLowerCase().contains("chrome")) {
                capability = DesiredCapabilities.chrome();
                capability.setBrowserName("chrome");
                capability.setPlatform(Platform.LINUX);
            }
            driver = new RemoteWebDriver(hostURL, capability);
        } else {
            if (SystemUtils.IS_OS_WINDOWS) {
                System.out.println("OS is Windows");
                if (browserName.toLowerCase().contains("firefox")) {
                    String propertyName = "webdriver.gecko.driver";
                    String unpackedPath = unpackedPrefix + "geckodriver32.exe";
                    String projectPath = windowsPathPrefix + "geckodriver32.exe";
                    pickDriver(propertyName, unpackedPath, projectPath);
                    driver = new FirefoxDriver();
                }
                if (browserName.toLowerCase().contains("ie32")) {
                    System.setProperty("webdriver.ie.driver", "IEDriverServer32.exe");
                    driver = new InternetExplorerDriver();
                }
                if (browserName.toLowerCase().contains("ie64")) {
                    System.setProperty("webdriver.ie.driver", "IEDriverServer64.exe");
                    driver = new InternetExplorerDriver();
                }

                if (browserName.toLowerCase().contains("chrome")) {
                    String property = "webdriver.chrome.driver";
                    String unpackedPath = unpackedPrefix + "chromedriver.exe";
                    String projectPath = windowsPathPrefix + "chromedriver.exe";
                    pickDriver(property, unpackedPath, projectPath);
                    driver = new ChromeDriver(cap);
                }
                if (browserName.toLowerCase().contains("edge")) {
                    System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");
                    driver = new EdgeDriver();
                }
            }
        }
        if (SystemUtils.IS_OS_LINUX) {
            System.out.println("OS is Linux");
            if (browserName.toLowerCase().contains("chrome")) {
                String propertyName = "webdriver.chrome.driver";
                String unpackedPath = unpackedPrefix + "chromedriver";
                String projectPath = linuxPathPrefix + "chromedriver";
                pickDriver(propertyName, unpackedPath, projectPath);
                driver = new ChromeDriver();
            }
            if (browserName.toLowerCase().contains("firefox")) {
                String propertyName = "webdriver.gecko.driver";
                String unpackedPath = unpackedPrefix + "geckodriver";
                String projectPath = linuxPathPrefix + "geckodriver";
                pickDriver(propertyName, unpackedPath, projectPath);
                driver = new FirefoxDriver();
            }
        }


        driver.manage().timeouts().implicitlyWait(webDriverImplicitlyWait, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }

    private static void pickDriver(String property, String path1, String path2) {
        if (driverExists(path1)) {
            System.setProperty(property, path1);
        }
        if (driverExists(path2)) {
            System.setProperty(property, path2);
        }
    }

    private static boolean driverExists(String pathToFile) {
        File tmpDir = new File(pathToFile);
        return tmpDir.exists() && tmpDir.isFile();
    }
}