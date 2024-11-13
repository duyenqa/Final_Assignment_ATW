package pages;

import core.BasePage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class DetailPage extends BasePage {
    List<WebElement> informationLemon;

    public DetailPage(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//li[@class='mm_products']/a[@class='dropmenu']/span[@class='text' and normalize-space(text())='Products']")
    private WebElement productsLink;

    @FindBy(xpath = "//li[@id='products_index']/a[@class='submenu']/span[@class='text' and normalize-space(text())='List Products']")
    private WebElement listProductitem;


    @FindBy(xpath = "//table[@id='PRData']/tbody[1]/tr[@id='3']/td[normalize-space(text())='Lemon']")
    private WebElement productLemon;

    public void resultExpected() throws InterruptedException {
        Thread.sleep(4000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(productsLink)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOf(listProductitem)).click();
        Thread.sleep(2000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(productLemon)).click();

        informationLemon = getDriver().findElements(By.xpath("(//div[@class='table-responsive']//table)[2]/tbody/tr"));
        for (WebElement element : informationLemon) {
            System.out.print(element.getText());
            System.out.println();
        }
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

            row = xssfSheet.getRow(4);
            cell = row.getCell(4);
            if (
                    informationLemon.get(0).isDisplayed() &&
                    informationLemon.get(1).isDisplayed() &&
                    informationLemon.get(2).isDisplayed() &&
                    informationLemon.get(3).isDisplayed() &&
                    informationLemon.get(4).isDisplayed() &&
                    informationLemon.get(5).isDisplayed() &&
                    informationLemon.get(6).isDisplayed() &&
                    informationLemon.get(7).isDisplayed() &&
                    informationLemon.get(8).isDisplayed() &&
                    informationLemon.get(9).isDisplayed() &&
                    informationLemon.get(10).isDisplayed() &&
                    informationLemon.get(11).isDisplayed() &&
                    informationLemon.get(12).isDisplayed()
            ){
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
