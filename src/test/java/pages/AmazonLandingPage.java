package pages;
import driverFactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import static java.util.ResourceBundle.getBundle;

public class AmazonLandingPage extends Page{
    private final ResourceBundle resourceBundle;
    public final String amazon_url;
    public final String deals;
    public final String expdeals;
    public final String expdealstext;
    public final String wholeFoodBtnLoc;
    public final String wholeFoodImgLoc;
    public final String wholeFoodText;
    public final String button1;
    public final String ebay_url;
    public final String youtube_url;
    public final String fox_url;
    public final String datafaction_url;

    public AmazonLandingPage() {
        resourceBundle = getBundle("pages.AmazonLandingPage");
        amazon_url = getResourceBundle().getString("amazon_url");
        deals = this.getResourceBundle().getString("deals");
        expdeals = this.getResourceBundle().getString("expdeals");
        expdealstext= this.getResourceBundle().getString("expdealstext");
        wholeFoodBtnLoc= this.getResourceBundle().getString("wholeFoodBtnLoc");
        wholeFoodImgLoc= this.getResourceBundle().getString("wholeFoodImgLoc");
        wholeFoodText= this.getResourceBundle().getString("wholeFoodText");
        button1= this.getResourceBundle().getString("button1");
        ebay_url= this.getResourceBundle().getString("ebay_url");
        youtube_url= this.getResourceBundle().getString("youtube_url");
        fox_url= this.getResourceBundle().getString("fox_url");
        datafaction_url= this.getResourceBundle().getString("datafaction_url");

    }
    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void clickBtnVerify(String browser,String clickBtn, String expectElement, String expectText) throws InterruptedException {
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.findElement(By.xpath(clickBtn)).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// implicity
//        Utility.waitUntilElementPresent(driver, 10 , By.xpath(expectElement)); //explicit wait
//        Wait waiter  = Utility.waitUntilElementPresentFluent(driver, 10, 2);//fluent wait
        String actual = driver.findElement(By.xpath(expectElement)).getText();
        String attributeText = driver.findElement(By.xpath(expectElement)).getAttribute("alt");
        if(actual.isEmpty()){
            System.out.println("Expected text: " + attributeText);
            Assert.assertTrue(attributeText.contains(expectText));
        }
        else {
            System.out.println("Expected text: " + actual);
            Assert.assertTrue(actual.contains(expectText), actual);
        }
    }

    public void selectBtnVerify(String browser,String clickBtn, String expectElement, String expectText) throws InterruptedException {
        WebDriver driver = DriverFactory.getDriver(browser);
        Actions select = new Actions(driver);
        select.moveToElement(driver.findElement(By.xpath(clickBtn))).build().perform();
        Thread.sleep(3000);
        String actual = driver.findElement(By.xpath(expectElement)).getText();
        String attributeText = driver.findElement(By.xpath(expectElement)).getAttribute("alt");
        if(actual.isEmpty()){
            System.out.println("Expected text: " + attributeText);
            Assert.assertTrue(attributeText.contains(expectText));
        }
        else {
            System.out.println("Expected text: " + actual);
            Assert.assertTrue(actual.contains(expectText), actual);
        }
    }
}
