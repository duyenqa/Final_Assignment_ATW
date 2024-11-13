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

public class PrintBarcodeOrLabelPage extends BasePage {
    List<WebElement> formGroups;

    public PrintBarcodeOrLabelPage(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//li[@class='mm_products']/a[@class='dropmenu']/span[@class='text' and normalize-space(text())='Products']")
    private WebElement productsLink;

    @FindBy(xpath = "//li[@id='products_print_barcodes']/a[@class='submenu']/span[@class='text' and normalize-space(text())='Print Barcode/Label']")
    private WebElement barcodeOrlabelLink;

    @FindBy(xpath = "//label[@for='add_item']")
    private WebElement addProduct;

    @FindBy(xpath = "//label[@for='style']")
    private WebElement styleProduct;

    public void resultExpected() throws InterruptedException {
        Thread.sleep(4000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(productsLink)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOf(barcodeOrlabelLink)).click();

        //Add Product
        getWebDriverWait().until(ExpectedConditions.visibilityOf(addProduct));
        System.out.println(addProduct.isDisplayed() ? "Add product is displayed" : "Add product is not displayed");

        //Style
        getWebDriverWait().until(ExpectedConditions.visibilityOf(styleProduct));
        System.out.println(styleProduct.isDisplayed() ? "Style is displayed" : "Style is not displayed");

        //Print (site name, product name, price, Currencies, Unit, Category, Variants, Product Image, Check promotional price)
        formGroups = getDriver().findElements(By.xpath("//form[@id='barcode-print-form']/div[@class='form-group'][2]/label"));

        for (WebElement element : formGroups) {
            System.out.println("Print: " + element.getText());
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

            row = xssfSheet.getRow(6);
            cell = row.getCell(4);
            if (
                    addProduct.isDisplayed() &&
                    styleProduct.isDisplayed() &&
                    formGroups.get(0).isDisplayed() &&
                    formGroups.get(1).isDisplayed() &&
                    formGroups.get(2).isDisplayed() &&
                    formGroups.get(3).isDisplayed() &&
                    formGroups.get(4).isDisplayed() &&
                    formGroups.get(5).isDisplayed() &&
                    formGroups.get(6).isDisplayed() &&
                    formGroups.get(7).isDisplayed() &&
                    formGroups.get(8).isDisplayed()){
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
