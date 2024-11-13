package test;

import core.BaseTest;
import core.ExcelUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddSalePage;
import pages.LoginPage;

public class VerifyAddSaleSuccessfully extends BaseTest {
    @Test(dataProvider = "data")
    public void test(String username, String password, String message) throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin("https://sma.tec.sh/admin/login");
        loginPage.login(username, password, true);

        AddSalePage addSalePage = new AddSalePage(getDriver());
        addSalePage.resultExpected();
        addSalePage.writeFile();
    }

    @DataProvider(name = "data")
    public Object[][] getData(){
        return ExcelUtils.getTableArray("C:\\Downloads\\VerifyLogin.xlsx", "Sheet2",1,3);
    }
}
