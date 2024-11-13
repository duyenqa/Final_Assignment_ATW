package pages;

import core.BasePage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class EditPage extends BasePage {
    public EditPage(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//li[@class='mm_products']/a[@class='dropmenu']/span[@class='text' and normalize-space(text())='Products']")
    private WebElement productsLink;

    @FindBy(xpath = "//li[@id='products_index']/a[@class='submenu']/span[@class='text' and normalize-space(text())='List Products']")
    private WebElement listProductitem;

    @FindBy(xpath = "//input[@aria-controls='PRData']")
    private WebElement searchProduct;

    @FindBy(xpath = "//div[@class='btn-group text-left']//button[1]")
    private WebElement actionsButton;

    @FindBy(xpath = "//li[contains(.,'Edit Product')]")
    private WebElement editProduct;

    public void resultExpected() throws InterruptedException {
        Thread.sleep(4000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(productsLink)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOf(listProductitem)).click();

        //Select the "Mouse" product and click to Edit Product
        Thread.sleep(2000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(searchProduct)).sendKeys("Mouse");
        Thread.sleep(4000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(actionsButton)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOf(editProduct)).click();

        // Product detail page displayed: Product Type, Product Name, Product Code, Slug, Barcode Symbology
        WebElement productTypelabel = getDriver().findElement(By.xpath("//label[@for='type']"));
        System.out.println(productTypelabel.isDisplayed() ? "Product type is displayed" : "Product type is not displayed");

        WebElement productNamelabel = getDriver().findElement(By.xpath("//label[@for='name']"));
        System.out.println(productNamelabel.isDisplayed() ? "Product name is displayed" : "Product name is not displayed");

        WebElement productCodelabel = getDriver().findElement(By.xpath("//label[@for='code']"));
        System.out.println(productCodelabel.isDisplayed() ? "Product code is displayed" : "Product code is not displayed");

        WebElement productSluglabel = getDriver().findElement(By.xpath("//label[@for='slug']"));
        System.out.println(productSluglabel.isDisplayed() ? "Product slug is displayed" : "Product slug is not displayed");

        Thread.sleep(3000);
        //Product type: Combo
        WebElement productType = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("s2id_type")));
        productType.click();
        WebElement selectProductType = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//ul[1]/li[2]")));
        selectProductType.click();

        Thread.sleep(3000);
        //Barcode Symbology: EAN8
        WebElement barcodeDropdown = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='s2id_barcode_symbology']/a")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", barcodeDropdown);
        WebElement optionBarcode = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='s2id_barcode_symbology']")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", optionBarcode);
        WebElement productBarcodelabel = getDriver().findElement(By.xpath("//label[@for='barcode_symbology']"));
        System.out.println(productBarcodelabel.isDisplayed() ? "Product barcode is displayed" : "Product barcode is not displayed");
        optionBarcode.click();
        WebElement inputSearch = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='s2id_autogen6_search']")));
        Thread.sleep(4000);
        inputSearch.sendKeys("EAN8");
        Thread.sleep(3000);
        inputSearch.sendKeys(Keys.ENTER);

        //Tax Method: Exclusive
        Thread.sleep(3000);
        WebElement taxDropdown = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='s2id_tax_method']")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", taxDropdown);
        taxDropdown.click();
        Thread.sleep(3000);
        WebElement selectValue = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(.,'Exclusive')]")));
        selectValue.click();

        //Click to Edit product
        Thread.sleep(3000);
        WebElement editProductButton = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Edit Product']")));
        editProductButton.click();
    }

    public boolean checkNavigateEditButton(){
        String actualUrl = getDriver().getCurrentUrl();
        if (actualUrl.equals("https://sma.tec.sh/admin/products/edit/13")) {
            return false;
        } else {
            return true;
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

            row = xssfSheet.getRow(5);
            cell = row.getCell(4);
            if (checkNavigateEditButton()){
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
