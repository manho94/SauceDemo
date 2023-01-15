package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Login;
import pages.Navigation;
import pages.ProductDetail;
import pages.ProductList;

import java.util.List;

import static common.Utils.*;

public class ProductDetailTest {
    WebDriver driver;
    Login login;
    ProductList productList;
    ProductDetail productDetail;
    Navigation navigation;

    final String STANDARD_USERNAME = "standard_user";
    final String PASSWORD = "secret_sauce";

    @BeforeClass
    void openBrowser(){
        driver = browser("chrome");
        login = new Login(driver);
        productDetail = new ProductDetail(driver);
        productList = new ProductList(driver);
        navigation = new Navigation(driver);
        login.openURL();
        login.inputCredential(STANDARD_USERNAME, PASSWORD);
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }

    @Test
    void verifyProductInfoInPdpPage(){
        int n = productList.countTotalNumberOfProductList();
        List<String> productInfo;
        List<String> productInfoPdp;

        for (int i = 0; i<n; i++){
            productInfo = productList.getInfoOfProductOnPlp(i+1);
            productList.clickOnProductNameOnProductListPage(i+1);
            productInfoPdp = productDetail.getInfoOfProductOnProductDetailPage();

            Assert.assertTrue(productList.isProductInfoInProductDetailSameAsProductList(productInfo,productInfoPdp));

            removeItemOfList(productInfo);
            removeItemOfList(productInfoPdp);
            productDetail.clickOnBackToProductBtn();
        }
    }

    @Test
    void verifyAddToCartButtonOnProductDetail(){
        int totalProduct = productList.countTotalNumberOfProductList();
        int totalCartItemsBeforeAdding;
        int totalCartItemsAfterAdding;

        for (int i=0; i<totalProduct; i++){
            productList.clickOnProductNameOnProductListPage(i+1);
            totalCartItemsBeforeAdding =  navigation.countNumberOfProductOnCartIcon();
            productDetail.clickOnAddToCartBtnOnPdp();
            totalCartItemsAfterAdding = navigation.countNumberOfProductOnCartIcon();

            Assert.assertTrue(totalCartItemsAfterAdding ==totalCartItemsBeforeAdding + 1 );

            productDetail.clickOnBackToProductBtn();
        }
        //Click On Remove to revert to Add to cart btn
        productList.clickOnRemoveButtonOnAllProductsOfProductList(totalProduct);
    }

    @Test
    void verifyRemoveBtnOnProductDetail(){
        int totalProduct = productList.countTotalNumberOfProductList();
        int totalCartItemsBeforeRemoving;
        int totalCartItemsAfterRemoving;

        //Click On Add To Cart Btn of All Products On Product List Page
        productList.clickOnAddToCartButtonOnAllProductsOfProductList(totalProduct);

        for (int i=0; i<totalProduct; i++){
            productList.clickOnProductNameOnProductListPage(i+1);
            totalCartItemsBeforeRemoving =  navigation.countNumberOfProductOnCartIcon();
            productDetail.clickOnRemoveBtnOnPdp();
            totalCartItemsAfterRemoving = navigation.countNumberOfProductOnCartIcon();

            Assert.assertTrue(totalCartItemsAfterRemoving ==totalCartItemsBeforeRemoving - 1 );

            productDetail.clickOnBackToProductBtn();
        }
    }
}
