package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

import static common.Utils.browser;
import static common.Utils.threadSleep;

public class CheckOutStep1Test {

    WebDriver driver;
    Login login;
    LoginTest loginTest;
    ProductList productList;
    Navigation navigation;
    Cart cart;
    CheckOutStep1 checkOutStep1;


    @DataProvider
    Object[][] invalidCardInfo(){
        return new Object[][]{
                {"", "Last Name 1","70000","Error: First Name is required"},
                {"First Name 1", "","70000","Error: Last Name is required"},
                {"First Name 1", "Last Name 1","","Error: Postal Code is required"},
                {"", "","","Error: First Name is required"},
        };
    }


    @BeforeClass
    void openBrowser(){
        driver = browser("chrome");
        login = new Login(driver);
        loginTest = new LoginTest();
        productList = new ProductList(driver);
        navigation = new Navigation(driver);
        cart = new Cart(driver);
        checkOutStep1 = new CheckOutStep1(driver);
        login.openURL();
        login.inputCredential(loginTest.STANDARD_USERNAME,loginTest.PASSWORD);
        productList.clickOnAddToCartButton(1);
        navigation.clickOnCartIcon();
        cart.clickOnCheckoutButton();
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }

    @Test(dataProvider = "invalidCardInfo")
    void paymentWhenInputInvalidCardInfo(String firstName, String lastName, String zipCode, String errorMessage){
        checkOutStep1.inputCardInfoAndPressCheckOut(firstName, lastName, zipCode);
        threadSleep(300);

        Assert.assertTrue(checkOutStep1.isErrorMessageCorrect(errorMessage));
    }

    @Test
    void verifyWhenClickingOnCancelBtn(){
        checkOutStep1.inputCardInfoButPressCancel();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/cart.html");
        cart.clickOnCheckoutButton();
    }

    @Test
    void paymentWithValidCardInfo(){
        checkOutStep1.inputCardInfoAndPressCheckOut("First Name 1", "Last Name 1", "70000");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");
    }



}
