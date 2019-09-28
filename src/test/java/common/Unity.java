package common;

import driverFactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Unity extends DriverFactory {

    public static void waitUntilElementPresent(WebDriver driver, int time, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, time);//explicit wait
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            throw new NoSuchElementException(locator.toString());
        }
    }

    public static void waitUntilPageLoaded(WebDriver driver, int time) {
        new WebDriverWait(driver, time).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForAjax() {
        ((JavascriptExecutor) driver).executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
    }

    public static Wait waitUntilElementPresentFluent(WebDriver driver, int time, int timePolling) {
        Wait wait = new FluentWait(driver) //explicit wait
        .withTimeout(time, TimeUnit.SECONDS).pollingEvery(timePolling, TimeUnit.SECONDS).ignoring(Exception.class);
        return wait;
    }


    public static void scrollToBottom(WebDriver driver, String element) throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        waitForAjax();
        waitUntilPageLoaded(driver,10);
        if (!element.isEmpty()){
            js.executeScript("arguments[0].scrollIntoView();", By.xpath(element));
        }
        else {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }

    public static void scrollToBottomSlowly(WebDriver driver, WebElement element) throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        waitForAjax();
        waitUntilPageLoaded(driver,10);
        do {
            js.executeScript("window.scrollBy(0,1000)");
            waitUntilPageLoaded(driver,10);


        } while (element.isDisplayed());
    }
}
