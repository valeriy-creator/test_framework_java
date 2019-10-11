package ui;

import common.Utility;
import driverFactory.DriverFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.VideoPlayerYouTubePage;

public class VideoPlayerYouTubeTest extends VideoPlayerYouTubePage {

    @Test()
    @Parameters({"browser"})
    public void verifyNavigationToYoutube(String browser) throws InterruptedException{
        navigationToYoutube(browser);
    }

    @Test()
    @Parameters({"browser"})
    public void verifyClickOnYoutubeByUrl(String browser) throws InterruptedException{
        navYoutubeClickVideo(browser, 5);
        Assert.assertTrue(driver.getCurrentUrl().contains("/watch?v="));
    }

    @Test()
    @Parameters({"browser"})
    public void verifyClickOnYoutubeByElement(String browser) throws InterruptedException{
        navYoutubeClickVideo(browser, 6);
        driver.findElement(By.xpath("//*[@id='movie_player']/div[20]/div[2]/div[1]/button")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='movie_player']/div[20]/div[2]/div[1]/button")).isDisplayed());
    }

    @Test()
    @Parameters({"browser"})
    public void verifyPlayPauseYoutube(String browser) throws InterruptedException{
        clickVideoPlayerButton(browser, 2, "//*[@title='Pause (k)']", "//*[@title='Play (k)']");
    }

    @Test()
    @Parameters({"browser"})
    public void verifyMuteUnmuteYoutube(String browser) throws InterruptedException{
        clickVideoPlayerButton(browser, 2, "//*[@title='Mute (m)']", "//*[@title='Unmute (m)']");
    }

    @Test()
    @Parameters({"browser"})
    public void verifyTheatedModeYoutube(String browser) throws InterruptedException{
        clickVideoPlayerButton(browser, 2, "//*[@title='Theater mode (t)']", "//*[@title='Default view (t)']");
    }

    @Test()
    @Parameters({"browser"})
    public void verifyCounterAdsYoutube(String browser) throws Exception{
        WebDriver driver = DriverFactory.getDriver(browser);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        navigationToYoutube(browser);
        driver.findElement(By.xpath("(//*[@id='thumbnail'])["+ 2 + "]")).click();
        Utility.waitUntilElementCounted(driver, "//*[contains(.,'0:02')]");
        Utility.waitUntilElementPresent(driver, 10, By.xpath("//*[@title='Pause (k)']"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@title='Pause (k)']")).isDisplayed());
        String startAds = (jse.executeScript("return arguments[0].innerHTML;",
                driver.findElement(By.xpath("//*[@class='ytp-bound-time-left']"))).toString());
        System.out.println("StartAds: "+startAds);
        Assert.assertTrue(startAds.contains("0:00"), startAds);
        String endAds = (jse.executeScript("return arguments[0].innerHTML;",
                        driver.findElement(By.xpath("//*[@class='ytp-bound-time-right']"))).toString());
        System.out.println("EndAds: "+endAds);
        Assert.assertTrue(!endAds.contains("0:00"), endAds);
        Assert.assertFalse(endAds.contains("0:00"), endAds);
        String currentAds = (jse.executeScript("return arguments[0].innerHTML;",
                        driver.findElement(By.xpath("//*[@class='ytp-time-current']"))).toString());
        System.out.println("CurrentAds: "+currentAds);
        Assert.assertFalse(currentAds.contains("0:00"), endAds);
    }

    @Test()
    @Parameters({"browser"})
    public void verifyYoutubeVolumeControl(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        navYoutubeClickPause(browser, 3);
        String unmute = driver.findElement(By.xpath("//*[@class='ytp-volume-panel']")).getAttribute("aria-valuetext");
        System.out.println("Expected  visible 'unmute' is: "+ unmute);
        Assert.assertTrue(unmute.contains("100% volume"));
        driver.findElement(By.xpath("//*[@class='ytp-mute-button ytp-button']")).click();
        String mute = driver.findElement(By.xpath("//*[@class='ytp-volume-panel']")).getAttribute("aria-valuetext");
        System.out.println("Expected  visible 'mute' is: "+ mute);
        Assert.assertTrue(mute.contains("100% volume muted"));
        driver.findElement(By.xpath("//*[@class='ytp-mute-button ytp-button']")).click();
        String unmuteagain = driver.findElement(By.xpath("//*[@class='ytp-volume-panel']")).getAttribute("aria-valuetext");
        System.out.println("Expected  visible 'unmuteagain' is: "+ unmuteagain);
        Assert.assertTrue(unmuteagain.contains("100% volume"));
    }

    @Test()
    @Parameters({"browser"})
    public void verifyYoutubeCCControl(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        navYoutubeClickPause(browser, 5);
        driver.findElement(By.xpath("//*[@class='ytp-subtitles-button ytp-button']")).click();
        Assert.assertTrue(driver.findElements(By.xpath("//*[@class='ytp-caption-segment']")).size()!=0);

    }
    @Test()
    @Parameters({"browser"})
    public void verifyYoutubeSettingControl(String browser) throws InterruptedException {
        WebDriver driver = DriverFactory.getDriver(browser);
        navYoutubeClickPause(browser, 3);
        String regular = "//*[@class='ytp-button ytp-settings-button']";
        String hd = "//*[@class='ytp-button ytp-settings-button ytp-hd-quality-badge']";
        String button;
        if (driver.findElements(By.xpath(regular)).size() != 0) {
            button = regular;
        } else {
            button = hd;
        }
        String settingOff = driver.findElement(By.xpath(button)).getAttribute("aria-expanded");
        System.out.println("Should be null: " + settingOff);
        Assert.assertTrue(settingOff == null);
        driver.findElement(By.xpath(button)).click();
        Thread.sleep(3000);
        String settingOn = driver.findElement(By.xpath(button)).getAttribute("aria-expanded");
        System.out.println("Should be 'true': " + settingOn);
        Assert.assertFalse(settingOn == null);
        Assert.assertTrue(settingOn.contains("true"));
    }

    @Test()
    @Parameters({"browser"})
    public void verifyYoutubeSetAnotationControl(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        navYoutubeClickPause(browser, 2);
        String regular = "//*[@class='ytp-button ytp-settings-button']";
        String hd = "//*[@class='ytp-button ytp-settings-button ytp-hd-quality-badge']";
        String button;
        try {
            if (driver.findElement(By.xpath(regular)).isDisplayed()) {
                button = regular;
            }
            else{
                button = hd;
            }
            driver.findElement(By.xpath(button)).click();
            String subtitles = driver.findElement(By.xpath("//div[@class='ytp-menuitem-label' and contains(., 'Subtitles/CC')]")).getText();
            System.out.println(subtitles);
            Assert.assertTrue(subtitles.contains("Subtitles/CC"));
        }
        catch (NoSuchElementException e){};
    }

    @Test()
    @Parameters({"browser"})
    public void verifyYoutubeSetAnotationControlHD(String browser) throws InterruptedException{
        WebDriver driver = DriverFactory.getDriver(browser);
        navYoutubeClickPause(browser, 2);
        Utility.checkIfElementPresentClick("//*[@class='ytp-button ytp-settings-button']",
                "//*[@class='ytp-button ytp-settings-button ytp-hd-quality-badge']");
        if(driver.findElement(By.xpath("//div[@class='ytp-menuitem-label' and contains(., 'Subtitles/CC')]")).isDisplayed());{
            String subtitles = driver.findElement(By.xpath("//div[@class='ytp-menuitem-label' and contains(., 'Subtitles/CC')]")).getText();
            System.out.println(subtitles);
            Assert.assertTrue(subtitles.contains("Subtitles/CC"));}
    }
}
