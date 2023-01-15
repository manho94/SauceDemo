package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Footer;
import pages.Login;
import pages.ProductList;

import static common.Utils.browser;

public class FooterTest {
    WebDriver driver;
    Footer footer;
    Login login;
    ProductList productList;

    final String STANDARD_USERNAME = "standard_user";
    final String PASSWORD = "secret_sauce";

    @BeforeClass
    void openBrowser(){
        driver = browser("chrome");
        login = new Login(driver);
        footer = new Footer(driver);
        productList = new ProductList(driver);
        login.openURL();
        login.inputCredential(STANDARD_USERNAME,PASSWORD);
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }

    @Test
    void verifySocialUrl(){
        Assert.assertTrue(footer.isTwitterUrlCorrect());
        Assert.assertTrue(footer.isFaceBookUrlCorrect());
        Assert.assertTrue(footer.isLinkedInUrlCorrect());
    }

    @Test
    void verifyCopyWritingCorrect(){
        Assert.assertTrue(footer.isCopyWritingCorrect());
    }

    @Test
    void verifyFooterImageDisplay(){
        Assert.assertTrue(footer.isFooterImageCorrect());
    }
}
