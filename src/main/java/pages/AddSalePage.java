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

public class AddSalePage extends BasePage {
    String resultMessage;

    public AddSalePage(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//li[contains(@class,'mm_sales ')]")
    private WebElement salesLink;

    @FindBy(xpath = "//li[@id='pos_sales']/following-sibling::li[1]")
    private WebElement addSalelink;

    @FindBy(xpath = "//div[@class='alert alert-success']")
    private WebElement messageUpdate;

    public void resultExpected() throws InterruptedException {
        Thread.sleep(4000);
        //Click to the Sales
        getWebDriverWait().until(ExpectedConditions.visibilityOf(salesLink)).click();
        //Click Add Sale
        getWebDriverWait().until(ExpectedConditions.visibilityOf(addSalelink)).click();

        //Input required input fields
        //Date
        WebElement dateField = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='sldate']")));
        dateField.clear();
        Thread.sleep(2000);
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("(//th[@class='today'])[3]"))).click();

        //Reference No
        WebElement refNofield = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='slref']")));
        refNofield.clear();
        refNofield.sendKeys("740060");
        //Biller
        WebElement billCombobox = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("s2id_slbiller")));
        billCombobox.click();
        WebElement selectBill = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='option' and @id='select2-result-label-13']")));
        selectBill.click();

        //Warehouse
        WebElement wareHousecb = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("s2id_slwarehouse")));
        wareHousecb.click();
        Thread.sleep(3000);
        WebElement selectWarehouse = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='select2-results']//li[2]")));
        selectWarehouse.click();
        //Customer
        WebElement customerCb = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("s2id_slcustomer")));
        customerCb.click();
        WebElement inputCustomer = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='s2id_autogen12_search']")));
        inputCustomer.click();
        inputCustomer.sendKeys("w");
        WebElement selectCustomer = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//ul[1]")));
        Thread.sleep(6000);
        selectCustomer.click();
        //add product
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='add_item' and @placeholder='Please add products to order list']"))).sendKeys("Kiwi");
        //Order Tax
        WebElement orderTaxcb = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='sltax2']/following-sibling::div[1]")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", orderTaxcb);
        Thread.sleep(3000);
        orderTaxcb.click();
        Thread.sleep(3000);
        WebElement selectOrdertax = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='select2-results' and @id='select2-results-4']/li[2]")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", selectOrdertax);
        selectOrdertax.click();

        //Order Discount
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("sldiscount"))).sendKeys("5");

        //Shipping
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("slshipping"))).sendKeys("2");

        //Sale Status
        WebElement saleStatus = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("s2id_slsale_status")));
        saleStatus.click();
        Thread.sleep(3000);
        WebElement selectStatus = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='select2-results' and @id='select2-results-5']/li[2]")));
        selectStatus.click();
        //Payment Term
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("slpayment_term"))).sendKeys("7");
        //Payment Status
        WebElement paymentStatus = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='slpayment_status']/following-sibling::div[1]")));
        paymentStatus.click();
        Thread.sleep(3000);
        WebElement selectPaymentstatus = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='select2-results' and @id='select2-results-6']/li[2]")));
        selectPaymentstatus.click();
        Thread.sleep(4000);

        //Sale Note
        WebElement editorSalenote = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@dir='ltr'])[2]/p")));
        Actions action1 = new Actions(getDriver());
        action1.doubleClick(editorSalenote).sendKeys("Fragile goods!").perform();

        //Staff Note
        WebElement editorStaffnote = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@dir='ltr'])[3]/p")));
        Actions action2 = new Actions(getDriver());
        action2.doubleClick(editorStaffnote).sendKeys("Birthday gift!").perform();

        //Click Submit
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("add_sale"))).click();

        Thread.sleep(3000);
        getWebDriverWait().until(ExpectedConditions.visibilityOf(messageUpdate));
        resultMessage = messageUpdate.getText();
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

            row = xssfSheet.getRow(8);
            cell = row.getCell(4);
            if (messageUpdate.isDisplayed()){
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
