package test;

import core.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PrintBarcodeOrLabelPage;

public class VerifyPrintBarcodeOrLabel extends BaseTest {
    @Test
    public void test() throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin("https://sma.tec.sh/admin/login/");
        loginPage.login("owner@tecdiary.com", "12345678", true);

        PrintBarcodeOrLabelPage printBarcodeOrLabelPage = new PrintBarcodeOrLabelPage(getDriver());
        printBarcodeOrLabelPage.resultExpected();
        printBarcodeOrLabelPage.writeFile();
    }
}
