package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import static common.Utils.browser;

public class ThankYouTest {
    WebDriver driver;
    Login login;
    Navigation navigation;
    LoginTest loginTest;
    ProductList productList;
    Cart cart;
    CheckOutStep1 checkOutStep1;
    CheckOutStep2 checkOutStep2;
    ThankYou thankYou;

    final String ALL_ITEMS_URL = "https://www.saucedemo.com/inventory.html";
    final String THANK_YOU_URL = "https://www.saucedemo.com/checkout-complete.html";


    @BeforeClass
    void openBrowser(){
        driver = browser("chrome");
        login = new Login(driver);
        navigation = new Navigation(driver);
        loginTest = new LoginTest();
        productList = new ProductList(driver);
        cart = new Cart(driver);
        checkOutStep1 = new CheckOutStep1(driver);
        checkOutStep2 = new CheckOutStep2(driver);
        thankYou = new ThankYou(driver);
        login.openURL();
        login.inputCredential(loginTest.STANDARD_USERNAME, loginTest.PASSWORD);
    }

    @AfterClass
    void closeBrowser(){
        driver.quit();
    }

    @AfterMethod
    void tearDown(){
        driver.get(ALL_ITEMS_URL);
    }

    @Test(priority = 10)
    void checkPaymentWhenCancelOnCheckOutStep2(){
        productList.clickOnAddToCartButton(1);
        navigation.clickOnCartIcon();
        cart.clickOnCheckoutButton();
        checkOutStep1.inputCardInfoAndPressCheckOut("First Name 1", "Last Name 1", "70000");
        checkOutStep2.clickOnCancelBtn();

        Assert.assertEquals(driver.getCurrentUrl(),ALL_ITEMS_URL);
    }

    @Test
    void checkPaymentSuccessWith1Product(){
        productList.clickOnAddToCartButton(1);
        navigation.clickOnCartIcon();
        cart.clickOnCheckoutButton();
        checkOutStep1.inputCardInfoAndPressCheckOut("First Name 1", "Last Name 1", "70000");
        checkOutStep2.clickOnFinishBtn();

        Assert.assertEquals(driver.getCurrentUrl(),THANK_YOU_URL);
        Assert.assertTrue(thankYou.isCheckOutCompleteTextDisplayed());
        Assert.assertTrue(thankYou.isThankYouTextDisplayed());
        Assert.assertTrue(thankYou.isThankYouDescriptionTextDisplayed());
        Assert.assertTrue(thankYou.isThankYouImageDisplayed());
        Assert.assertTrue(thankYou.isBackHomeBtnDisplayed());
        Assert.assertTrue(navigation.isNumberOfProductsResetOnCartAfterCheckOutSuccessfully());
    }

    @Test
    void checkPaymentSuccessWithMoreProducts(){
        productList.clickOnAddToCartButton(1);
        //Add the 2nd product to card
        productList.clickOnAddToCartButton(1);
        navigation.clickOnCartIcon();
        cart.clickOnCheckoutButton();
        checkOutStep1.inputCardInfoAndPressCheckOut("First Name 1", "Last Name 1", "70000");
        checkOutStep2.clickOnFinishBtn();

        Assert.assertEquals(driver.getCurrentUrl(),THANK_YOU_URL);
        Assert.assertTrue(thankYou.isCheckOutCompleteTextDisplayed());
        Assert.assertTrue(thankYou.isThankYouTextDisplayed());
        Assert.assertTrue(thankYou.isThankYouDescriptionTextDisplayed());
        Assert.assertTrue(thankYou.isThankYouImageDisplayed());
        Assert.assertTrue(thankYou.isBackHomeBtnDisplayed());
        Assert.assertTrue(navigation.isNumberOfProductsResetOnCartAfterCheckOutSuccessfully());
    }

    @Test
    void clickOnBackHomeButtonAfterPayment(){
        productList.clickOnAddToCartButton(1);
        //Add the 2nd product to card
        productList.clickOnAddToCartButton(1);
        navigation.clickOnCartIcon();
        cart.clickOnCheckoutButton();
        checkOutStep1.inputCardInfoAndPressCheckOut("First Name 1", "Last Name 1", "70000");
        checkOutStep2.clickOnFinishBtn();
        thankYou.clickOnBackHomeBtn();

        Assert.assertEquals(driver.getCurrentUrl(),ALL_ITEMS_URL);

    }

}
