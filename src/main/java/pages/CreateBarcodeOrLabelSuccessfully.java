package pages;

import core.BasePage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class CreateBarcodeOrLabelSuccessfully extends BasePage {
    public CreateBarcodeOrLabelSuccessfully(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//li[@class='mm_products']/a[@class='dropmenu']/span[@class='text' and normalize-space(text())='Products']")
    private WebElement productsLink;


    @FindBy(xpath = "//li[@id='products_print_barcodes']/a[@class='submenu']/span[@class='text' and normalize-space(text())='Print Barcode/Label']")
    private WebElement barcodeOrlabelLink;

    @FindBy(xpath = "//input[@placeholder='Add Item']")
    private WebElement addItemField;

    @FindBy(xpath = "//input[@value='Update']")
    private WebElement updateProductbutton;

    @FindBy(xpath = "//span[@class='product_image']/img")
    private WebElement imageElement;

    @FindBy(xpath = "//span[@class='product_image']/following-sibling::span[1]")
    private WebElement siteElement;

    @FindBy(xpath = "//span[@class='barcode_site']/following-sibling::span[1]")
    private WebElement nameElement;

    @FindBy(xpath = "//span[@class='barcode_name']/following-sibling::span[1]")
    private WebElement priceElement;

    @FindBy(xpath = "//span[@class='barcode_price']/following-sibling::span[1]")
    private WebElement unitElement;

    @FindBy(xpath = "//span[@class='barcode_unit']/following-sibling::span[1]")
    private WebElement categoryElement;

    @FindBy(xpath = "//span[@class='barcode_category']/following-sibling::span[1]/img")
    private WebElement barcodeElement;

    public void resultExpected() throws InterruptedException {
        Thread.sleep(4000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(productsLink)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOf(barcodeOrlabelLink)).click();

        //Add Kiwi product
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(addItemField)).sendKeys("Kiwi");

        //Style: 18 per sheet (a4) (2.5" x 1.835")
        Thread.sleep(4000);
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("s2id_style"))).click();
        WebElement inputSearch = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("s2id_autogen1_search")));
        inputSearch.click();
        Thread.sleep(3000);
        inputSearch.sendKeys("18 per sheet (a4) (2.5\" x 1.835\")");
        Thread.sleep(6000);
        inputSearch.sendKeys(Keys.ENTER);

        //select print (site name, product name, price, Currencies, Unit, Category, Variants, Product Image, Check promotional price)
        List<WebElement> checkboxes = getDriver().findElements(By.xpath("//form[@id='barcode-print-form']/div[@class='form-group'][2]/div[@class='icheckbox_square-blue']"));

        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }

        // Click to Update button
        Thread.sleep(3000);
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(updateProductbutton)).click();

        Thread.sleep(6000);
        //The page display bar code with some info:
        //+ product_image
        getWebDriverWait().until(ExpectedConditions.visibilityOf(imageElement));
        String imageUrl = imageElement.getAttribute("src");
        System.out.println("Image URL: " + imageUrl);
        //+ barcode_site: Stock Manager Advance
        getWebDriverWait().until(ExpectedConditions.visibilityOf(siteElement));
        String barcode_site = siteElement.getText();
        System.out.println("Barcode site:" + barcode_site);
        //+ barcode_name: Kiwi
        getWebDriverWait().until(ExpectedConditions.visibilityOf(nameElement));
        String barcode_name = nameElement.getText();
        System.out.println("Barcode name: " + barcode_name);
        //+ barcode_price: Price USD: 3.90, ERU: 2.86,
        getWebDriverWait().until(ExpectedConditions.visibilityOf(priceElement));
        String barcode_price = priceElement.getText();
        System.out.println("Barcode price: " + barcode_price);
        //+ barcode_unit: Unit: 4
        getWebDriverWait().until(ExpectedConditions.visibilityOf(unitElement));
        String barcode_unit = unitElement.getText();
        System.out.println("Barcode unit: " + barcode_unit);
        //+ barcode_category: Category: Fruits
        getWebDriverWait().until(ExpectedConditions.visibilityOf(categoryElement));
        String barcode_category = categoryElement.getText();
        System.out.println("Barcode category: " + barcode_category);
        //+ barcode_image
        getWebDriverWait().until(ExpectedConditions.visibilityOf(barcodeElement));
        String barcode_image = barcodeElement.getText();
        System.out.println("Barcode image: " + barcode_image);
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

            row = xssfSheet.getRow(7);
            cell = row.getCell(4);
            if (
                    imageElement.isDisplayed() &&
                    siteElement.isDisplayed() &&
                    nameElement.isDisplayed() &&
                    priceElement.isDisplayed() &&
                    unitElement.isDisplayed() &&
                    categoryElement.isDisplayed() &&
                    barcodeElement.isDisplayed()){
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
