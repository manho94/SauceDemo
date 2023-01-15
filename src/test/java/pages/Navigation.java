package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static common.Utils.waitElementClickAble;

public class Navigation {
    private WebDriver driver;

    private By burgerMenu = By.id("react-burger-menu-btn");
    private By logoImage = By.className("app_logo");
    private By resetAppStateButton = By.id("reset_sidebar_link");
    private By closeBurgerMenuIcon = By.id("react-burger-cross-btn");
    private By allItemsMenuLink = By.id("inventory_sidebar_link");
    private By aboutMenuLink = By.id("about_sidebar_link");
    private By cartIcon = By.id("shopping_cart_container");
    private By logOutMenuLink = By.id("logout_sidebar_link");
    private By totalCartNumber = By.className("shopping_cart_badge");
    private By wrapBurgerMenu = By.className("bm-menu-wrap");

    public Navigation(WebDriver driver) {
        this.driver = driver;
    }

    public Navigation() {
    }

    public boolean isBurgerMenuDisplayed(){
        boolean result = false;
        if (driver.findElements(burgerMenu).size() > 0){
            result = true;
        }
        return result;
    }

    public boolean isLogoDisplayed(){
        boolean result = false;
        if (driver.findElements(logoImage).size() > 0){
            result = true;
        }
        return result;
    }

    public boolean isCartIconDisplayed(){
        boolean result = false;
        if (driver.findElements(cartIcon).size() > 0){
            result = true;
        }
        return result;
    }

    public void clickOnBurgerMenu(){
        if (driver.findElement(wrapBurgerMenu).getAttribute("aria-hidden").equalsIgnoreCase("true")){
            waitElementClickAble(driver.findElement(burgerMenu)).click();
        }

    }

    public void clickOnResetAppStateBtn(){
        waitElementClickAble(driver.findElement(resetAppStateButton)).click();
    }

    public void closeBurgerMenu(){
        waitElementClickAble(driver.findElement(closeBurgerMenuIcon)).click();
    }

    public void clickOnAllItemMenu(){
        waitElementClickAble(driver.findElement(allItemsMenuLink)).click();
    }

    public boolean isRedirectToCorrectLink(String expectedURL){
        boolean result = false;
        if (driver.getCurrentUrl().equalsIgnoreCase(expectedURL)){
            result = true;
        }
        return result;
    }

    public void clickOnAboutMenu(){
        waitElementClickAble(driver.findElement(aboutMenuLink)).click();
    }

    public  void clickOnCartIcon(){
        waitElementClickAble(driver.findElement(cartIcon)).click();
    }

    public void clickOnLogOutMenu(){
        waitElementClickAble(driver.findElement(logOutMenuLink)).click();
    }

    public int countNumberOfProductOnCartIcon(){
        int numberProductInCart;
        if (driver.findElements(totalCartNumber).size() == 0){
            numberProductInCart = 0;
        }else{
            numberProductInCart = Integer.parseInt(driver.findElement(totalCartNumber).getText());
        }
        return numberProductInCart;
    }

    public boolean isNumberOfProductsResetOnCartAfterCheckOutSuccessfully(){
        boolean result = false;
        if(countNumberOfProductOnCartIcon() ==0){
            result = true;
        }
        return result;
    }




}
