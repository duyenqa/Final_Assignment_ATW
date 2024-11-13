package test;

import core.BaseTest;
import core.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class VerifySuccessfullyLoggedIn extends BaseTest {
    @Test(dataProvider = "data")
    public void test(String username, String password, String message) throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin("https://sma.tec.sh/admin/login");
        loginPage.login(username, password, true);

        DashboardPage dashboardPage = new DashboardPage(getDriver());
        dashboardPage.writeFile();
        Assert.assertTrue(dashboardPage.verifyMessage(message));
    }

    @DataProvider(name = "data")
    public Object[][] getData(){
        return ExcelUtils.getTableArray("C:\\Downloads\\VerifyLogin.xlsx", "Sheet1",1,3);
    }
}
