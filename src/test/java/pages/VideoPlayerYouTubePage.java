package pages;

import common.Utility;
import driverFactory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

public class VideoPlayerYouTubePage extends Page {
    private final ResourceBundle resourceBundle;
    public final String youtube_url;

    public VideoPlayerYouTubePage() {
        resourceBundle = getBundle("pages.VideoPlayerYoutubePage");
        youtube_url = getResourceBundle().getString("youtube_url");
        }
    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }


    public void navigationToYoutube(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.get(youtube_url);
        Assert.assertTrue(driver.getCurrentUrl().contains(youtube_url));
    }

    public void navYoutubeClickVideo(String browser, int video) throws InterruptedException{
        navigationToYoutube(browser);
        WebDriver driver = DriverFactory.getDriver(browser);
        driver.findElement(By.xpath("(//*[@id='thumbnail'])["+ video + "]")).click();
        Utility.waitUntilElementPresent(driver, 10, By.xpath("//*[@title='Pause (k)']"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@title='Pause (k)']")).isDisplayed());
    }

    public void navYoutubeClickPause(String browser, int video) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        clickVideoPlayerButton(browser, video, "//*[@title='Theater mode (t)']", "//*[@title='Default view (t)']");
        Utility.waitUntilElementPresent(driver, 10, By.xpath("//*[@title='Pause (k)']"));
        driver.findElement(By.xpath("//*[@title='Pause (k)']")).click();
    }

    public void clickVideoPlayerButton(String browser, int video, String butBefore, String butAfter) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        navYoutubeClickVideo(browser, video);
        Utility.waitUntilElementPresent(driver, 10, By.xpath(butBefore));
        Assert.assertTrue(driver.findElement(By.xpath(butBefore)).isDisplayed());
        driver.findElement(By.xpath(butBefore)).click();
        Utility.waitUntilElementPresent(driver, 10, By.xpath(butAfter));
        Assert.assertTrue(driver.findElement(By.xpath(butAfter)).isDisplayed());
    }
}
