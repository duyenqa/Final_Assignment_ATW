package test;

import core.BaseTest;
import org.testng.annotations.Test;
import pages.ListProductPage;
import pages.LoginPage;

public class VerifyListProducts extends BaseTest {
    @Test
    public void test() throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin("https://sma.tec.sh/admin/login/");
        loginPage.login("owner@tecdiary.com", "12345678", true);

        ListProductPage listProductPage = new ListProductPage(getDriver());
        listProductPage.resultExpected();
        listProductPage.writeFile();
    }
}
