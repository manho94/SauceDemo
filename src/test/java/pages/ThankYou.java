package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static common.Utils.waitElementClickAble;

public class ThankYou {
    private WebDriver driver;

    private String checkOutCompleteLabel = "CHECKOUT: COMPLETE!";
    private String thankYouLabel = "THANK YOU FOR YOUR ORDER";
    private String thankYouDescription = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
    private String thankYouImagePath = "https://www.saucedemo.com/static/media/pony-express.46394a5d.png";
    private By BackHomeButton = By.id("back-to-products");
    private By checkOutCompleteLabelLocator = By.className("header_secondary_container");
    private By thankYouTextLocator = By.className("complete-header");
    private By completeTextLocator = By.className("complete-text");
    private By thankYouImageLocator = By.className("pony_express");


    public ThankYou(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnBackHomeBtn(){
        waitElementClickAble(driver.findElement(BackHomeButton)).click();
    }

    public boolean isCheckOutCompleteTextDisplayed(){
        boolean result = false;
        if(driver.findElement(checkOutCompleteLabelLocator).getText().equalsIgnoreCase(checkOutCompleteLabel)){
            result = true;
        }
        return result;
    }

    public boolean isThankYouTextDisplayed(){
        boolean result = false;
        if(driver.findElement(thankYouTextLocator).getText().equalsIgnoreCase(thankYouLabel)){
            result = true;
        }
        return result;
    }

    public boolean isThankYouDescriptionTextDisplayed(){
        boolean result = false;
        if(driver.findElement(completeTextLocator).getText().equalsIgnoreCase(thankYouDescription)){
            result = true;
        }
        return result;
    }

    public boolean isThankYouImageDisplayed(){
        boolean result = false;
        if(driver.findElement(thankYouImageLocator).getAttribute("src").equalsIgnoreCase(thankYouImagePath)){
            result = true;
        }
        return result;
    }

    public boolean isBackHomeBtnDisplayed(){
        boolean result = false;
        if(driver.findElements(BackHomeButton).size() == 1){
            result = true;
        }
        return result;
    }




}
