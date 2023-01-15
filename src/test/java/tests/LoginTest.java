package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Login;

import static common.Utils.*;


public class LoginTest {
    WebDriver driver;
    Login loginPage;
    final String STANDARD_USERNAME = "standard_user";
    final String PASSWORD = "secret_sauce";

    @DataProvider
    Object[][] invalidCredential(){
        return new Object[][]{
                {"standard_user1", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "secret_sauce1", "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user1", "secret_sauce1", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"", "", "Epic sadface: Username is required"},
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."}
        };
    }

    @BeforeClass
    void openBrowser(){
        driver = browser("chrome");
        loginPage = new Login(driver);
    }

    @BeforeMethod
    void openLoginURL(){
        loginPage.openURL();
    }


    @AfterClass
    void closeBrowser(){
        driver.quit();

    }

    @Test
    void withValidCredential(){
        loginPage.inputCredential(STANDARD_USERNAME, PASSWORD);

        Assert.assertTrue(loginPage.isCurrentURLInventory());

    }

    @Test(dataProvider = "invalidCredential")
    void withInvalidCredential(String userName, String passWord, String errorMessage){
        loginPage.inputCredential(userName, passWord);

        Assert.assertTrue(loginPage.isErrorMessageCorrect(errorMessage));

    }

}
