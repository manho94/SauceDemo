package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static common.Utils.*;

public class ProductDetail {
    WebDriver driver;

    private By productImage = By.xpath("//div[@class='inventory_details_img_container']/img");
    private By productName = By.className("inventory_details_name");
    private By productDes = By.className("inventory_details_desc");
    private By productPriceCurrency = By.className("inventory_details_price");
    private By backToProductBtn = By.className("inventory_details_back_button");
    private By addToCartBtnStateLabel = By.xpath("//div[@class='inventory_details_desc_container']/button");
    private By addToCartButton = By.className("btn_primary");
    private By RemoveButton = By.xpath("//button[@class='btn btn_secondary btn_small btn_inventory']");

    public ProductDetail(WebDriver driver) {
        this.driver = driver;
    }

    public ProductDetail() {
    }

    public List<String> getInfoOfProductOnProductDetailPage(){
        List<String> productInfoPdp;
        String productImagePdp = driver.findElement(productImage).getAttribute("src");
        String productNamePdp = getProductNameOnProductDetailPage();
        String productDesPdp = driver.findElement(productDes).getText();
        String productPricePdbCurrency = driver.findElement(productPriceCurrency).getText();
        String productPricePdp = productPricePdbCurrency.replace("$", "");
        String addToCartBtnStateLabel = driver.findElement(this.addToCartBtnStateLabel).getText();
        productInfoPdp = new LinkedList<>(Arrays.asList(productImagePdp,productNamePdp,productDesPdp,productPricePdp,addToCartBtnStateLabel));

        return productInfoPdp;
    }

    public String getProductNameOnProductDetailPage(){
        return waitElementDisplayed(productName).getText();
    }

    public void clickOnBackToProductBtn(){
        waitElementClickAble(driver.findElement(backToProductBtn)).click();
    }

    public void clickOnAddToCartBtnOnPdp(){
        waitElementClickAble(driver.findElement(addToCartButton)).click();
    }

    public void clickOnRemoveBtnOnPdp(){
        waitElementClickAble(driver.findElement(RemoveButton)).click();
    }


}
