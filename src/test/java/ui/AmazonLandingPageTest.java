package ui;

import common.Unity;
import driverFactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AmazonLandingPage;

import java.util.List;

public class AmazonLandingPageTest extends AmazonLandingPage{

    @Test()
    @Parameters({"browser"})
    public void amazon_today_deals_btn(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.get(amazon_url);
        clickBtnVerify(browser,deals, expdeals, expdealstext);
    }

    @Test()
    @Parameters({"browser"})
    public void amazon_third_btn(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.get(amazon_url);
        clickBtnVerify(browser, deals, expdeals, expdealstext);

    }

    @Test()
    @Parameters({"browser"})
    public void amazon_whoole_btn(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.get(amazon_url);
        clickBtnVerify(browser,wholeFoodBtnLoc, wholeFoodImgLoc, wholeFoodText);
    }

    @Test()
    @Parameters({"browser"})
    public void amazon_sighin_btn(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.get(amazon_url);
        clickBtnVerify(browser,"//*[@id=\"nav-link-accountList\"]/span[1]",
                "//*[@id=\"nav-al-wishlist\"]/a[10]/span",
                "Your Hearts");
    }

    @Test()
    @Parameters({"browser"})
    public void ebay_sighin_btn(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.get(ebay_url);
        selectBtnVerify(browser,"//*[@id=\"mainContent\"]/div[1]/ul/li[3]",
                "//*[@id=\"mainContent\"]/div[1]/ul/li[3]/div[2]/div[1]/div[1]/ul/li[7]/a",
                "My Garage");
    }

    @Test()
    @Parameters({"browser"})
    public void amazon_sighin_error_msg(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.get(amazon_url);
        clickBtnVerify(browser,"//*[@id=\"nav-signin-tooltip\"]/a/span",
                "//h1[@class='a-spacing-small']",
                "Sign-In");
        driver.findElement(By.name("email")).sendKeys("qastarting@gmail.com");
        driver.findElement(By.name("email")).sendKeys(Keys.ENTER);
        String error = driver.findElement(By.xpath("//*[@id='auth-error-message-box']/div/h4")).getText();
        WebElement el = driver.findElement(By.xpath("//*[@id='auth-error-message-box']/div/h4"));

        Assert.assertTrue(error.contains("There was a problem") && el.isDisplayed(), error);
        Assert.assertFalse(error.contains("There prlkbnl'knflkanoblem"), error);

        Assert.assertTrue(el.isDisplayed());
        Assert.assertFalse(!el.isDisplayed());


        Assert.assertFalse(! el.isDisplayed(), el.toString());

        Assert.assertTrue(error.contains("There pr,JBFBAKJSBFoblem")||el.isDisplayed(), error);
    }

//        @Test()
//    @Parameters({"browser"})
//    public void verify_youtube_buttom_last_elements(String browser) throws Exception{
//        WebDriver driver = DriverFactory.getDriver(browser);
//        driver.manage().window().maximize();
//        driver.get(fox_url);
//        Thread.sleep(10000);
//
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//        WebElement title69 = driver.findElement(By.xpath("(//*[@id=\"title\"])[69]"));
////        jse.executeScript("window.scrollBy(0,1000)");
//
//
//            //This will scroll the page till the element is found
////            jse.executeScript("arguments[0].scrollIntoView();", title69);
////        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//
//            Unity.scrollToBottomSlowly(driver,title69);
////
//    }

//    @Test()
//    @Parameters({"browser"})
//    public void verify_youtube_buttom_last_element2(String browser) throws InterruptedException{
//        WebDriver driver = DriverFactory.getDriver(browser);
//        driver.manage().window().maximize();
//        driver.get(youtube_url);
//        Actions scroll = new Actions(driver);
//        WebElement title = driver.findElement(By.tagName("body"));
//        WebElement title5 = driver.findElement(By.xpath("(//*[@id=\"title\"])[17]"));
//        WebElement title69 = driver.findElement(By.xpath("(//*[@id=\"title\"])[69]"));
//        JavascriptExecutor jse = (JavascriptExecutor) driver;
//        title5.sendKeys(Keys.PAGE_DOWN);
//
//        while (title69.isDisplayed()) {
//        boolean continueScrolling = false;
//        do {
//            title.sendKeys(Keys.PAGE_DOWN);
//            Thread.sleep(2000);
//            String top = String.valueOf(jse.executeScript("return document.scrollTop;"));
//            continueScrolling =! top
////            scroll.sendKeys(Keys.PAGE_DOWN).build().perform();
////            scroll.release().perform();
//        }
//        while (continueScrolling);
//    }
}
