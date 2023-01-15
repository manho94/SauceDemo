package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import static common.Utils.browser;

public class CheckOutStep2Test {
    WebDriver driver;
    LoginTest loginTest;
    Login login;
    ProductList productList;
    Navigation navigation;
    Cart cart;
    CheckOutStep1 checkOutStep1;
    CheckOutStep2 checkOutStep2;

    final String ALL_ITEMS_URL = "https://www.saucedemo.com/inventory.html";

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
        while (productList.getNumberOfProductHasRemoveButton()>0){
            productList.clickOnRemoveFromCartButton(1);
        }
    }

    @Test
    void verifyInfoOnCheckOutStep2WhenCheckOutWith1Product(){
        double productPriceOnPlp = productList.getProductPriceOnPlp(1);
        productList.clickOnAddToCartButton(1);
        navigation.clickOnCartIcon();
        cart.clickOnCheckoutButton();
        checkOutStep1.inputCardInfoAndPressCheckOut("First Name 1", "Last Name 1", "70000");

        double totalPriceItem = checkOutStep2.getTotalPriceItemOnWebsite();
        double taxValueOnWebsite = checkOutStep2.getTaxOnWebsite();
        double totalValue = checkOutStep2.getTotalPriceItemAndTaxOnWebsite();
        double taxBasedOnItemPrice = checkOutStep2.calculateTaxBasedOnTotalProductItemPrice(totalPriceItem);

        Assert.assertTrue(productPriceOnPlp == totalPriceItem);
        Assert.assertTrue(taxValueOnWebsite == taxBasedOnItemPrice);
        Assert.assertTrue(totalValue == totalPriceItem + taxValueOnWebsite);
        Assert.assertTrue(checkOutStep2.isPaymentInfoLabelDisplayed());
        Assert.assertTrue(checkOutStep2.isPaymentInfoValueDisplayed());
        Assert.assertTrue(checkOutStep2.isShippingInfoLabelDisplayed());
        Assert.assertTrue(checkOutStep2.isShippingInfoValueDisplayed());

    }

    @Test
    void verifyInfoOnCheckOutStep2WhenCheckOutWithMoreProducts(){
        double productPriceOnPlp = productList.getProductPriceOnPlp(1) + productList.getProductPriceOnPlp(2);
        //add to cart the 1rst product
        productList.clickOnAddToCartButton(1);
        //add to cart the 2nd product
        productList.clickOnAddToCartButton(1);
        navigation.clickOnCartIcon();
        cart.clickOnCheckoutButton();
        checkOutStep1.inputCardInfoAndPressCheckOut("First Name 1", "Last Name 1", "70000");

        double totalPriceItem = checkOutStep2.getTotalPriceItemOnWebsite();
        double taxValueOnWebsite = checkOutStep2.getTaxOnWebsite();
        double totalValue = checkOutStep2.getTotalPriceItemAndTaxOnWebsite();
        double taxBasedOnItemPrice = checkOutStep2.calculateTaxBasedOnTotalProductItemPrice(totalPriceItem);

        Assert.assertTrue(productPriceOnPlp == totalPriceItem);
        Assert.assertTrue(taxValueOnWebsite == taxBasedOnItemPrice);
        Assert.assertTrue(totalValue == totalPriceItem + taxValueOnWebsite);
        Assert.assertTrue(checkOutStep2.isPaymentInfoLabelDisplayed());
        Assert.assertTrue(checkOutStep2.isPaymentInfoValueDisplayed());
        Assert.assertTrue(checkOutStep2.isShippingInfoLabelDisplayed());
        Assert.assertTrue(checkOutStep2.isShippingInfoValueDisplayed());

    }


}
