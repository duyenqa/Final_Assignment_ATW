package pages;

import core.BasePage;
import core.ExcelUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.apache.poi.hssf.util.HSSFColor.*;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver d){
        super(d);
    }

    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement username;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@name='remember']/following-sibling::ins[1]")
    private WebElement rememberPassword;

    public void login(String u, String p, boolean r) throws InterruptedException {
        getWebDriverWait().until(ExpectedConditions.visibilityOf(username));
        username.clear();
        username.sendKeys(u);
        password.clear();
        password.sendKeys(p);

        if (r) {
            rememberPassword.click();
        }
        writeFile();
        Thread.sleep(3000);
        loginButton.click();
    }

    public void navigateToLogin(String url){
        getDriver().get(url);
        getDriver().manage().window().maximize();
    }

    public void writeFile(){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("C:\\Downloads\\Final_Assignment-Requirement.xlsx"));
            FileOutputStream fileOutputStream = null;
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheet("TestCase");
            XSSFFont font = xssfWorkbook.createFont();
            XSSFCellStyle style = xssfWorkbook.createCellStyle();

            XSSFRow row = null;
            XSSFCell cell = null;

            row = xssfSheet.getRow(1);
            cell = row.getCell(4);
            if (username.isDisplayed() && password.isDisplayed() && loginButton.isDisplayed() || rememberPassword.isDisplayed()){
                font.setFontName("Times New Roman");
                font.setFontHeight(12);
                font.setBold(true);
                style.setFont(font);
                //align text
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                //color text
                font.setColor(IndexedColors.GREEN.getIndex());
                cell.setCellStyle(style);
                cell.setCellValue("Pass");
            }else {
                font.setFontName("Times New Roman");
                font.setFontHeight(12);
                font.setBold(true);
                style.setFont(font);
                //align text
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                //color text
                font.setColor(IndexedColors.RED.getIndex());
                cell.setCellStyle(style);
                cell.setCellValue("Fail");
            }
            fileOutputStream = new FileOutputStream("C:\\Downloads\\Final_Assignment-Requirement.xlsx");
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
