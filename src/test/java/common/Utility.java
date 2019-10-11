package common;

import driverFactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Utility extends DriverFactory {

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

    public void mouseHover(String element) throws InterruptedException{
        WebElement button = driver.findElement(By.xpath(element));
        Actions clickAction = new Actions(driver).click(button);
        clickAction.build().perform();
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

    public static void waitUntilElementCounted(WebDriver driver, String element) {
        try {
            Assert.assertTrue(driver.findElements(By.xpath(element)).size() != 0);
        } catch (
                org.openqa.selenium.NoSuchElementException e) {
        }
    }

    public WebElement fluentWait(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return  foo;
    };

    public static void checkIfElementPresent(String element) {
        try {
            if (driver.findElement(By.xpath(element)).isDisplayed()) {
                WebElement el = driver.findElement(By.xpath(element));
                System.out.println("Found element: "+ el.getAttribute("aria-label"));
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }
    }

    public static void checkIfElementPresentClick(String element, String element2) {
        try {
            if (driver.findElement(By.xpath(element)).isDisplayed()) {
                WebElement el = driver.findElement(By.xpath(element));
                System.out.println("Found element: "+ el.getAttribute("aria-label"));
                el.click();
            }
            else{
                driver.findElement(By.xpath(element2)).click();
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }
    }
}
