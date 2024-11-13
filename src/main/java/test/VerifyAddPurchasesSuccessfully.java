package test;

import core.BaseTest;
import core.ExcelUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddPurchasePage;
import pages.LoginPage;

public class VerifyAddPurchasesSuccessfully extends BaseTest {

    @Test(dataProvider = "data")
    public void test(String username, String password, String message) throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLogin("https://sma.tec.sh/admin/login/");
        loginPage.login(username, password, true);

        AddPurchasePage addPurchasePage = new AddPurchasePage(getDriver());
        addPurchasePage.resultExpected();
        addPurchasePage.writeFile();
    }

    @DataProvider(name = "data")
    public Object[][] getData(){
        return ExcelUtils.getTableArray("C:\\Downloads\\VerifyLogin.xlsx", "Sheet3",1,3);
    }
}
