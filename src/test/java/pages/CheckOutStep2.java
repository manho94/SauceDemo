package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;
import static common.Utils.*;

public class CheckOutStep2 {
    private WebDriver driver;
    final private double TAX_RATE = 0.08;
    String paymentValueContainText = "SauceCard";
    String shippingValueContainText = "FREE PONY EXPRESS DELIVERY!";
    private By itemPriceLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalPriceLabel = By.className("summary_total_label");
    private By paymentInfoLabel = By.xpath("//div[contains(text(),'Payment Information:')]");
    private By paymentInfoValue = By.xpath("(//div[@class='summary_value_label'])[1]");
    private By shippingInfoLabel = By.xpath("//div[contains(text(),'Shipping Information:')]");
    private By shippingInfoValue = By.xpath("(//div[@class='summary_value_label'])[2]");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");

    public CheckOutStep2(WebDriver driver) {
        this.driver = driver;
    }

    public double getTotalPriceItemOnWebsite(){
        String totalPriceItemCurrency = driver.findElement(itemPriceLabel).getText();
        return Double.parseDouble(totalPriceItemCurrency.replace("Item total: $",""));
    }

    public double getTaxOnWebsite(){
        String taxValueCurrency = driver.findElement(taxLabel).getText();
        return Double.parseDouble(taxValueCurrency.replace("Tax: $",""));
    }

    public double getTotalPriceItemAndTaxOnWebsite(){
        String totalValueCurrency = driver.findElement(totalPriceLabel).getText();
        return Double.parseDouble(totalValueCurrency.replace("Total: $", ""));

    }

    public double calculateTaxBasedOnTotalProductItemPrice(double totalPriceItem){
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(totalPriceItem*TAX_RATE));
    }

    public boolean isPaymentInfoLabelDisplayed(){
        boolean result = false;
        if (driver.findElements(paymentInfoLabel).size()>0){
            result = true;
        }
        return result;
    }

    public boolean isPaymentInfoValueDisplayed(){
        boolean result = false;
        if (driver.findElement(paymentInfoValue).getText().contains(paymentValueContainText)){
            result = true;
        }
        return result;
    }

    public boolean isShippingInfoLabelDisplayed(){
        boolean result = false;
        if (driver.findElements(shippingInfoLabel).size()>0){
            result = true;
        }
        return result;
    }

    public boolean isShippingInfoValueDisplayed(){
        boolean result = false;
        if (driver.findElement(shippingInfoValue).getText().contains(shippingValueContainText)){
            result = true;
        }
        return result;
    }

    public void clickOnFinishBtn(){
        waitElementClickAble(driver.findElement(finishButton)).click();
    }

    public void clickOnCancelBtn(){
        waitElementClickAble(driver.findElement(cancelButton)).click();
    }


}
