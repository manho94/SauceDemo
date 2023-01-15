package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.util.*;
import java.util.stream.Collectors;

import static common.Utils.*;

public class ProductListTest {
    WebDriver driver;
    Login login;
    ProductList productList;
    Navigation navigation;
    ProductDetail productDetail;

    final String STANDARD_USERNAME = "standard_user";
    final String PASSWORD = "secret_sauce";
    final String PRODUCT_FILE_NAME = "src/test/resources/Saucedemo-ProductList.xlsx";


    @BeforeClass
    void openProductListURL(){
        driver = browser("chrome");
        login = new Login(driver);
        productList = new ProductList(driver);
        navigation = new Navigation(driver);
        productDetail = new ProductDetail(driver);
        login.openURL();
        login.inputCredential(STANDARD_USERNAME,PASSWORD);
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }

    @Test
    void verifyTotalNumberOfProduct(){
        int totalNumber = productList.countTotalNumberOfProductList();
        Assert.assertEquals(totalNumber,6);
    }

    @Test
    void verifyInformationOfEachProduct(){
        Assert.assertTrue(productList.isInfoOfProductOnWebSameAsTheExelFile());
    }

    @Test
    void verifyAddToCartButtonOnProductList(){
        navigation.clickOnBurgerMenu();
        navigation.clickOnResetAppStateBtn();
        navigation.closeBurgerMenu();

        int numberProductInCart;
        int numberProductInCartAfter;
        int totalProduct = productList.countTotalNumberOfProductList();

        for (int i=0; i< totalProduct; i++){
            numberProductInCart = navigation.countNumberOfProductOnCartIcon();
            productList.clickOnAddToCartButton(i +1);
            numberProductInCartAfter = navigation.countNumberOfProductOnCartIcon();

            Assert.assertTrue(numberProductInCartAfter == numberProductInCart + 1);

            productList.clickOnRemoveFromCartButton(1);

        }
    }

    @Test
    void verifyRemoveButtonOnProductList(){
        int numberProductInCartAfterAdding;
        int numberProductInCartAfterRemoving;
        navigation.clickOnBurgerMenu();
        navigation.clickOnResetAppStateBtn();
        navigation.closeBurgerMenu();

        int totalProduct = productList.countTotalNumberOfProductList();
        for (int i=0; i<totalProduct; i++){
            productList.clickOnAddToCartButton(i+1);
            numberProductInCartAfterAdding = navigation.countNumberOfProductOnCartIcon();
            productList.clickOnRemoveFromCartButton(1);
            numberProductInCartAfterRemoving = navigation.countNumberOfProductOnCartIcon();

            Assert.assertTrue(numberProductInCartAfterRemoving ==numberProductInCartAfterAdding -1 );
        }
    }

    @Test
    void verifyDetailURLOfEachProduct(){
        int numberOfProduct = productList.countTotalNumberOfProductList();
        List<Product> allProducts = productList.getListAllProductInExelFile(PRODUCT_FILE_NAME);

        for (int i = 0; i< numberOfProduct; i++){
            productList.clickOnProductNameOnProductListPage(i+1);

            Assert.assertEquals(driver.getCurrentUrl(),allProducts.get(i).getProductDetailUrl().trim());
            driver.navigate().back();

        }
    }

    @Test
    void verifyOptionOnSortDropdown(){
        Assert.assertTrue(productList.isSortDropDownOptionCorrect());
    }

    @Test
    void verifySortNameAToZ(){
        List<String> productNameListBeforeSort = productList.getProductNameListOnPLP();
        productList.selectSortDropdownByValue("az");
        List<String> productNameListAfterSort = productList.getProductNameListOnPLP();
        List<String> sortedListProductNameBefore = productNameListBeforeSort.stream().sorted().collect(Collectors.toList());

        Assert.assertTrue(are2StringArrayListEqualIncludeOrder(productNameListAfterSort,sortedListProductNameBefore));
    }

    @Test
    void verifySortNameZToA(){
        List<String> productNameListBeforeSort = productList.getProductNameListOnPLP();
        productList.selectSortDropdownByValue("za");
        List<String> productNameListAfterSort = productList.getProductNameListOnPLP();
        List<String> sortedListProductNameBefore = productNameListBeforeSort.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        Assert.assertTrue(are2StringArrayListEqualIncludeOrder(productNameListAfterSort,sortedListProductNameBefore));
    }

    @Test
    void verifySortPriceLowToHigh(){
        List<Double> productPriceListBeforeSort = productList.getProductPriceListOnPlp();
        productList.selectSortDropdownByValue("lohi");
        List<Double> productPriceListAfterSort = productList.getProductPriceListOnPlp();
        List<Double> sortedListProductPriceBefore = productPriceListBeforeSort.stream().sorted().collect(Collectors.toList());

        Assert.assertTrue(are2DoubleArrayListEqualIncludeOrder(productPriceListAfterSort,sortedListProductPriceBefore));
    }

    @Test
    void verifySortPriceHighToLow(){
        List<Double> productPriceListBeforeSort = productList.getProductPriceListOnPlp();
        productList.selectSortDropdownByValue("hilo");
        List<Double> productPriceListAfterSort = productList.getProductPriceListOnPlp();
        List<Double> sortedListProductPriceBefore = productPriceListBeforeSort.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        Assert.assertTrue(are2DoubleArrayListEqualIncludeOrder(productPriceListAfterSort,sortedListProductPriceBefore));
    }





}
