package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public BasePage(WebDriver d){
        this.driver = d;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver(){
        return driver;
    }

    public WebDriverWait getWebDriverWait(){
        return webDriverWait;
    }
}
