package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class CheckOutStep1 {

    private WebDriver driver;

    private By firstNameTextField = By.id("first-name");
    private By lastNameTextField = By.id("last-name");
    private By postalCodeTextField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By errorMessageLabel = By.className("error-message-container");
    private By cancelBtn = By.id("cancel");


    public CheckOutStep1(WebDriver driver) {
        this.driver = driver;
    }
    public void inputCardInfoAndPressCheckOut(String firstName, String lastName, String zipCode){
        driver.findElement(firstNameTextField).sendKeys(Keys.CONTROL,"a", Keys.DELETE);
        driver.findElement(firstNameTextField).sendKeys(firstName);
        driver.findElement(lastNameTextField).sendKeys(Keys.CONTROL,"a", Keys.DELETE, lastName);
        driver.findElement(lastNameTextField).sendKeys(lastName);
        driver.findElement(postalCodeTextField).sendKeys(Keys.CONTROL,"a", Keys.DELETE, zipCode);
        driver.findElement(postalCodeTextField).sendKeys(zipCode);
        driver.findElement(continueButton).click();
    }

    public boolean isErrorMessageCorrect(String expectedErr){
        boolean result = false;
        if (driver.findElement(errorMessageLabel).getText().equalsIgnoreCase(expectedErr)){
            result = true;
        }
        return result;
    }

    public void inputCardInfoButPressCancel(){
        driver.findElement(firstNameTextField).sendKeys("");
        driver.findElement(lastNameTextField).sendKeys("");
        driver.findElement(postalCodeTextField).sendKeys("");
        driver.findElement(cancelBtn).click();
    }



}
