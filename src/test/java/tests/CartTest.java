package tests;

import common.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pages.*;
import common.Log;

import java.util.List;

import static common.Utils.*;

//@Listeners(TestListener.class)
public class CartTest {
    WebDriver driver;
    Login login;
    ProductList productList;
    ProductDetail productDetail;
    Navigation navigation;
    Cart cart;
    final String STANDARD_USERNAME = "standard_user";
    final String PASSWORD = "secret_sauce";
    int totalProductOnProductList;
    List<Product> listOfProductInfoOnProductListPage;

    @BeforeClass
    void openBrowser(){
        driver = browser("chrome");
        login = new Login(driver);
        productList = new ProductList(driver);
        navigation = new Navigation(driver);
        productDetail = new ProductDetail(driver);
        cart = new Cart(driver);
        login.openURL();
        login.inputCredential(STANDARD_USERNAME,PASSWORD);
        totalProductOnProductList = productList.countTotalNumberOfProductList();
        listOfProductInfoOnProductListPage = productList.getInfoOfAllProductOnProductListPage(totalProductOnProductList);
        productList.clickOnAddToCartButtonOnAllProductsOfProductList(totalProductOnProductList);
        navigation.clickOnCartIcon();
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }

    @Test(description = "Verify All Elements Is Displayed On Cart Page")
    void verifyElementsOnCartPage(){
        Assert.assertTrue(cart.isYourCardTextDisplayedOnCartPage());
        Assert.assertTrue(cart.isQtyTextDisplayedOnCartPage());
        Assert.assertTrue(cart.isDescTextDisplayedOnCartPage());
        Assert.assertTrue(cart.isTotalProductInCartPageSameAsNumberOnCartIcon());
        Assert.assertTrue(cart.isContinueShoppingBtnDisplayed());
        Assert.assertTrue(cart.isCheckOutBtnDisplayed());

    }

    @Test(description = "Verify Info Of Each Products Item On Cart Page")
    void verifyInfoOfEachProductsItemOnCartPage(){
        Assert.assertTrue(cart.isInfoProductOnCartPageSameAsInfoAddedProductFromProductList(listOfProductInfoOnProductListPage));
    }

    @Test(description = "Verify When Clicking on Continue Shopping Button")
    void verifyWhenClickingOnContinueShoppingButton(){
        cart.clickOnContinueShoppingButton();
        Assert.assertTrue(cart.isUrlAfterClickingOnContinueShoppingBtnCorrect());
        navigation.clickOnCartIcon();
    }

    @Test(description = "Verify When Clicking On CheckOut Button")
    void verifyWhenClickingOnCheckoutBtn(){
        cart.clickOnCheckoutButton();
        Assert.assertTrue(cart.isUrlAfterClickingOnCheckoutBtnCorrect());
        navigation.clickOnCartIcon();
    }

    @Test(description = "Verify When Clicking On Remove Button")
    void verifyWhenClickingOnRemoveBtn(){
        int totalProductOnCartIconAfterRemoving;
        int totalProductInCartPageAfterRemoving;
        int totalProductOnCartIconBeforeRemoving = navigation.countNumberOfProductOnCartIcon();
        int totalProductInCartPageBeforeRemoving = cart.getTotalProductOnCartPage();
        int numberCount = totalProductInCartPageBeforeRemoving;

        for (int i = 0; i<numberCount; i++){
            cart.clickOnRemoveBtn();

            totalProductOnCartIconAfterRemoving = navigation.countNumberOfProductOnCartIcon();
            totalProductInCartPageAfterRemoving = cart.getTotalProductOnCartPage();

            Assert.assertTrue(totalProductOnCartIconAfterRemoving == totalProductOnCartIconBeforeRemoving - 1);
            Assert.assertTrue(totalProductInCartPageAfterRemoving == totalProductInCartPageBeforeRemoving - 1 );

            totalProductOnCartIconBeforeRemoving-=1;
            totalProductInCartPageBeforeRemoving-=1;
        }

    }

    @Test (description = "Verify When Clicking on Product Name on Cart Page")
    void verifyWhenClickingOnProductNameOnCartPage(){
        int totalProductOnCart = cart.getTotalProductOnCartPage();
        for (int i=0; i<totalProductOnCart; i++){
            String productNameOnCartPage = cart.getProductNameOnCartPage(i+1);
            cart.clickOnProductNameOnCartPage(i +1);
            String productNameOnPDP = productDetail.getProductNameOnProductDetailPage();

            Assert.assertEquals(productNameOnPDP, productNameOnCartPage);

            driver.navigate().back();

        }
    }



}
