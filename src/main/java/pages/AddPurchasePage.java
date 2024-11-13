package pages;

import core.BasePage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class AddPurchasePage extends BasePage {
    String resultMessage;

    public AddPurchasePage(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//li[contains(@class,'mm_purchases')]")
    private WebElement purchasesLink;

    @FindBy(xpath = "//span[normalize-space(text())='Add Purchase']")
    private WebElement addPurchaselink;

    @FindBy(xpath = "//div[@class='alert alert-success']")
    private WebElement messageAddPurchase;

    public void resultExpected() throws InterruptedException {
        Thread.sleep(4000);
        //Click to the Purchases
        getWebDriverWait().until(ExpectedConditions.visibilityOf(purchasesLink)).click();
        //Click Add Purchase
        getWebDriverWait().until(ExpectedConditions.visibilityOf(addPurchaselink)).click();

        //Input required input fields
        //Date
        WebElement dateField = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='podate']")));
        dateField.clear();
        Thread.sleep(2000);
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("(//th[@class='today'])[3]"))).click();

        //Reference No
        WebElement refNofield = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='poref']")));
        refNofield.clear();
        refNofield.sendKeys("HPSSB05");

        //Warehouse
        WebElement wareHousecb = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("s2id_powarehouse")));
        wareHousecb.click();
        Thread.sleep(3000);
        WebElement selectWarehouse = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='select2-results']//li[2]")));
        selectWarehouse.click();
        //Status
        WebElement status = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("s2id_postatus")));
        status.click();
        Thread.sleep(3000);
        WebElement selectStatus = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='select2-results' and @id='select2-results-2']/li[3]")));
        selectStatus.click();

        //Supplier
        WebElement supplier = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("s2id_posupplier")));
        supplier.click();
        WebElement inputSupplier = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//label[text()='Supplier']/following-sibling::input)[2]")));
        inputSupplier.click();
        inputSupplier.sendKeys("Fruits Supply M");
        WebElement selectSupplier = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//ul[1]")));
        Thread.sleep(6000);
        selectSupplier.click();

        //add product
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='add_item' and @placeholder='Please add products to order list']"))).sendKeys("Lemon");

        //Order Tax
        WebElement orderTaxcb = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='potax2']/following-sibling::div[1]")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", orderTaxcb);
        Thread.sleep(3000);
        orderTaxcb.click();

        //Discount (5/5%)
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("podiscount"))).sendKeys("5");

        //Shipping
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("poshipping"))).sendKeys("1");

        //Payment Term
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("popayment_term"))).sendKeys("7");

        //Note
        WebElement editorNote = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@dir='ltr']//p[1]")));
        Actions action1 = new Actions(getDriver());
        action1.doubleClick(editorNote).sendKeys("Pack carefully for me!").perform();

        //Click to the Submit
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("add_pruchase"))).click();

        Thread.sleep(3000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(messageAddPurchase));
        resultMessage = messageAddPurchase.getText();
        System.out.println(resultMessage);

        Assert.assertTrue(verifyMessage(resultMessage));
    }

    public boolean verifyMessage(String expectedMessage){
        WebElement message = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-success']")));
        String actualMessage = message.getText();
        return actualMessage.equals(expectedMessage);
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

            row = xssfSheet.getRow(10);
            cell = row.getCell(4);
            if (messageAddPurchase.isDisplayed()){
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
