package mechanics.system.credentials;

import mechanics.system.jar.Args;
import mechanics.ui.pageObjects.LogInPage;
import mechanics.ui.utils.WebDriverFactory;
import mechanics.ui.utils.WebDriverManager;
import org.openqa.selenium.WebDriver;

import javax.xml.bind.PropertyException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Alex Storm on 08.06.2017.
 */
public class Collector {
    private Properties prop = new Properties();
    private InputStream input = null;
    private static final String credentialFile = "credentials.properties";

    void readEmailPassword(String role) {
        try {
            input = new FileInputStream(credentialFile);
            // load a properties file
            prop.load(input);
            if (prop.get(role + "_email") != null && !prop.getProperty(role + "_email").isEmpty()) {
                Users.getUser(role).setEmail(prop.getProperty(role + "_email"));
            } else {
                throw new PropertyException("ERR: property '" + role + "_email' not found");
            }
            if (!prop.getProperty(role + "_password").isEmpty()) {
                Users.getUser(role).setPassword(prop.getProperty(role + "_password"));
            } else {
                throw new PropertyException("ERR: property '" + role + "_password' not found");
            }
            Users.getUser(role).setRole(role);
        } catch (IOException | PropertyException io) {
            io.printStackTrace();
            System.exit(1);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void receiveAWSKeys(String role) {
        String browserName = Args.browser;
        Boolean useGrid = Args.grid;
        WebDriver driver = WebDriverFactory.createInstance(browserName, useGrid);
        WebDriverManager.setWebDriver(driver);
        getCreds(role);
        Users.getUser(role).setCredentialsReceiveTimeNow();
        if (driver != null) {
            driver.quit();
        }
    }

    private void getCreds(String role) {
        LogInPage log = new LogInPage();
        log.getToIoTPage();
        log.loginVsAmazonOrGoogle(Users.getUser(role).getEmail(), Users.getUser(role).getPassword(), role);
        Users.getUser(role).setAwsKeys(log.getRequestSigns());
    }
}
