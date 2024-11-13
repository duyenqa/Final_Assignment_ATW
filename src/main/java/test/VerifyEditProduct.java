package test;

import core.BaseTest;
import org.testng.annotations.Test;
import pages.EditPage;
import pages.LoginPage;

public class VerifyEditProduct extends BaseTest {
    @Test
    public void test() throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin("https://sma.tec.sh/admin/login/");
        loginPage.login("owner@tecdiary.com", "12345678", true);

        EditPage editPage = new EditPage(getDriver());
        editPage.resultExpected();
        editPage.writeFile();
    }
}
