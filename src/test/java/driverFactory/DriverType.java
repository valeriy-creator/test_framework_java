package driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.opera.*;
import org.openqa.selenium.remote.*;
import java.io.*;
import java.util.*;
import static driverFactory.DriverFactory.*;


public enum DriverType implements DriverSetup {
    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            capabilities.setCapability("networkConnectionEnabled", true);
            capabilities.setCapability("browserConnectionEnabled", true);
            capabilities.setCapability("disable-web-security", true);
            capabilities.setJavascriptEnabled(true);
            return capabilities;
        }
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities){
//            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/SeleniumFoxXlsCreated/resources/geckodriver");
            System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "");
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver(capabilities);
        }
    },
    CHROME {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("disable-infobars");
            options.addArguments("headless");
            options.addArguments("disable-extensions");
            options.setExperimentalOption("prefs", prefs);
            capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
            options.addArguments("--disable-notifications");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
            prefs.put("profile.password_manage_enabled", "true");
            prefs.put("credentials_enable_service", true);
            prefs.put("password_manager_enabled", true);
            prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put( "profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 );
            prefs.put("download.prompt_for_download", false);
            prefs.put("profile.password_manage_enabled", "false");
            capabilities.setCapability("chrome.prefs", prefs);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            return capabilities;
        }
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities){
//            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/webdrivers/chromedriver");
//            System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "");
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(capabilities);
        }
    },

    Opera {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.opera();
            return capabilities;
        }
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities){
//            File file = new File(new File(String.valueOf(DriverType.class.getClassLoader().getResource("operadriver"))).getPath());
//            System.setProperty("webdriver.opera.driver", file.toString());
            return new OperaDriver(capabilities);
        }
    }

}
