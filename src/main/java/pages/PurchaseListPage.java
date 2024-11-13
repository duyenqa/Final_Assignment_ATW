package pages;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PurchaseListPage extends BasePage {
    public PurchaseListPage(WebDriver d) {
        super(d);
    }

    @FindBy(xpath = "//li[contains(@class,'mm_purchases')]")
    private WebElement purchasesLink;

    @FindBy(xpath = "//span[normalize-space(text())='List Purchases']")
    private WebElement listPurchaselink;

    public void resultExpected() throws InterruptedException {
        Thread.sleep(4000);
        //Click to the Sales
        getWebDriverWait().until(ExpectedConditions.visibilityOf(purchasesLink)).click();
        //Click Add Sale
        getWebDriverWait().until(ExpectedConditions.visibilityOf(listPurchaselink)).click();

        Thread.sleep(3000);
        List<WebElement> tablePurchase = getDriver().findElements(By.xpath("//table[@id='POData']/tbody[1]/tr"));
        System.out.println("Displayed " + tablePurchase.size() + " purchases");
        Thread.sleep(4000);

        List<WebElement> rows = getDriver().findElements(By.xpath("//table[@id='POData']/tbody[1]/tr[position() <= 9]"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath("//td[2]"));
            if (cells.size() > 1) {
                String dateText = cells.get(1).getText(); // Assuming the date is in the second column
                try {
                    dateFormat.parse(dateText);
                    System.out.println("Date is in correct format: " + dateText);
                } catch (ParseException e) {
                    System.out.println("Date is in incorrect format: " + dateText);
                }
            }
        }

        //check sort desc
        Thread.sleep(4000);
        List<WebElement> elements = getDriver().findElements(By.xpath("//table[@id='POData']/tbody[1]/tr[position() <= 9]/td[3]"));
        List<String> referenceNumbers = elements.stream().map(WebElement::getText).toList();

        boolean isDescending = referenceNumbers.stream().reduce((a, b) -> {
            if (a.compareTo(b) > 0) {
                return a;
            } else {
                throw new RuntimeException("Reference No are not sorted in descending order");
            }
        }).isPresent();

        if (isDescending) {
            System.out.println("Reference No are sorted in descending");
        } else {
            System.out.println("Reference No are not sorted in descending !");
        }
    }
}
