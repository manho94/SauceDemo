package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static common.Utils.*;

public class Login {

    WebDriver driver;
    private final String loginURL = "https://www.saucedemo.com/";
    private final String inventoryURL = "https://www.saucedemo.com/inventory.html";
    private By userNameField = By.id("user-name");
    private By passWordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.className("error-message-container");

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    public Login() {
    }

    public void openURL(){
        driver.get(loginURL);
    }

    public void inputCredential(String userName,String passWord){
        driver.findElement(userNameField).sendKeys(userName);
        driver.findElement(passWordField).sendKeys(passWord);
        waitElementClickAble(driver.findElement(loginButton)).click();
    }

    public boolean isCurrentURLInventory(){
        boolean result = false;
        if (driver.getCurrentUrl().equalsIgnoreCase(inventoryURL)){
            result = true;
        }
        return result;
    }

    public boolean isErrorMessageCorrect(String expectedErr){
        boolean result = false;
        if (driver.findElement(errorMessage).getText().equalsIgnoreCase(expectedErr)){
            result = true;
        }
        return result;
    }


}
