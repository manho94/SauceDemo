package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Login;
import pages.Navigation;

import static common.Utils.*;

public class NavigationTest {
    WebDriver driver;
    Login login;
    Navigation navigation;
    final String STANDARD_USERNAME = "standard_user";
    final String PASSWORD = "secret_sauce";
    final String INVENTORY_URL = "https://www.saucedemo.com/inventory.html";
    final String ABOUT_URL = "https://saucelabs.com/";
    final String CART_URL = "https://www.saucedemo.com/cart.html";
    final String LOGIN_URL = "https://www.saucedemo.com/";

    @BeforeClass
    void openBrowser(){
        driver = browser("chrome");
        login = new Login(driver);
        navigation = new Navigation(driver);
        login.openURL();
        login.inputCredential(STANDARD_USERNAME,PASSWORD);
    }

    @AfterClass
    void closeBrowser(){
        driver.quit();
    }

    @Test
    void verifyAllElementOnNavigationPage(){
        Assert.assertTrue(navigation.isBurgerMenuDisplayed());
        Assert.assertTrue(navigation.isLogoDisplayed());
        Assert.assertTrue(navigation.isCartIconDisplayed());
    }

    @Test
    void verifyRedirectOfAllItemsMenu(){
        navigation.clickOnBurgerMenu();
        navigation.clickOnAllItemMenu();
        navigation.closeBurgerMenu();

        Assert.assertTrue(navigation.isRedirectToCorrectLink(INVENTORY_URL));
    }

    @Test
    void verifyRedirectOfAboutMenu(){
        navigation.clickOnBurgerMenu();
        navigation.clickOnAboutMenu();

        Assert.assertTrue(navigation.isRedirectToCorrectLink(ABOUT_URL));
        driver.navigate().back();
    }

    @Test
    void verifyRedirectOfCartIcon(){
        navigation.clickOnCartIcon();
        Assert.assertTrue(navigation.isRedirectToCorrectLink(CART_URL));
    }

    @Test
    void verifyResetAppState(){
        navigation.clickOnBurgerMenu();
        navigation.clickOnResetAppStateBtn();
        int numberOfProductInCartAfterResetting = navigation.countNumberOfProductOnCartIcon();
        navigation.closeBurgerMenu();

        Assert.assertTrue(numberOfProductInCartAfterResetting == 0);
    }

    @Test(priority = 20)
    void verifyLogout(){
        navigation.clickOnBurgerMenu();
        navigation.clickOnLogOutMenu();

        Assert.assertTrue(navigation.isRedirectToCorrectLink(LOGIN_URL));

    }
}
