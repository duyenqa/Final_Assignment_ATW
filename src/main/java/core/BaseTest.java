package core;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
    private WebDriver driver;

    @Parameters("browser")
    @BeforeTest
    public void beforeTest(String browser) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setPlatform(Platform.WINDOWS);
        desiredCapabilities.setBrowserName(browser);

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
        }

        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            desiredCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        }

        driver = new RemoteWebDriver(new URL("http://localhost:4444"),desiredCapabilities);
    }

    @AfterTest
    public void afterTest(){
        System.out.println("End Program!");
//        driver.quit();
    }

    public WebDriver getDriver(){
        return driver;
    }
}
