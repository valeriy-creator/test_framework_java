package driverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    public static String methodName;
    public static WebDriver driver;
    private static ThreadLocal<DriverThread> driverThread;
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        driverThread = new ThreadLocal<DriverThread>() {
            @Override
            protected DriverThread initialValue() {
                return new DriverThread();
            }
        };
    }
    public static WebDriver getDriver(String browser){
        driverThread.get().getDriver(browser).manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver = driverThread.get().getDriver(browser);
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        System.out.println("");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpReport(Method method){
        methodName = method.getName();
        String timeStamp = new SimpleDateFormat("HH.mm a", Locale.US).format(new Date());
        System.out.print("\n"+ANSI_GREEN_BACKGROUND + "Method: "+methodName +", time: "+timeStamp + ANSI_RESET+"\n");
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() throws Exception {
        if(null!=driverThread.get()){
            driverThread.get().quitDriver();}
//        if(driver!=null)
//            driver.manage().deleteAllCookies();
    }

    public void disableAnimations() {
        String script = "var styleEl = document.createElement('style'); " +
                "styleEl.textContent = '*{ " +
                "    transition-property: none !important; " +
                "    -o-transition-property: none !important; " +
                "    -moz-transition-property: none !important; " +
                "    -ms-transition-property: none !important; " +
                "    -webkit-transition-property: none !important; " +
                "    transform: none !important; " +
                "    -o-transform: none !important; " +
                "    -moz-transform: none !important; " +
                "    -ms-transform: none !important; " +
                "    -webkit-transform: none !important; " +
                "    animation: none !important; " +
                "    -o-animation: none !important; " +
                "    -moz-animation: none !important; " +
                "    -ms-animation: none !important; " +
                "    -webkit-animation: none !important; " +
                "}'; " +
                "document.head.appendChild(styleEl); ";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    protected boolean isElementPresent(By by) {
        return driver.findElements(by).size() > 0;
    }

    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    @AfterClass(alwaysRun = true)
    public static void quitDriver() {
        if(null!=driverThread.get()){
            driverThread.get().quitDriver();}
//        Utility.SendMailSSLWithAttachment();
    }

    @AfterSuite(alwaysRun = true)
    public static void closeDriverObjects() {
        if(null!=driverThread.get()){
            driverThread.get().quitDriver();}
    }
}
