package pages;

import core.BasePage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//div[@class='alert alert-success']//p[1]")
    private WebElement alertMessage;

    @FindBy(xpath = "//h2[normalize-space(text())='Overview Chart']")
    private WebElement overviewChart;

    @FindBy(xpath = "//h2[normalize-space(text())='Quick Links']")
    private WebElement quickLinks;

    @FindBy(xpath = "//h2[normalize-space(text())='Latest Five']")
    private WebElement latestFive;

    @FindBy(xpath = "//h2[normalize-space(text())='Best Sellers (Sep-2024)']")
    private WebElement bestSellers;

    @FindBy(xpath = "//div[@class='alert alert-success']//p[1]")
    private WebElement message;

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

            row = xssfSheet.getRow(2);
            cell = row.getCell(4);
            if (alertMessage.isDisplayed() && overviewChart.isDisplayed() && quickLinks.isDisplayed() && latestFive.isDisplayed() && bestSellers.isDisplayed()){
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

    public boolean verifyMessage(String expectedMessage){
        getWebDriverWait().until(ExpectedConditions.visibilityOf(message));
        String actualMessage = message.getText();
        return actualMessage.equals(expectedMessage);
    }
}
