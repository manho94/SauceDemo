package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

import static common.Utils.*;

public class Cart {
    WebDriver driver;
    Navigation navigation;
    private By yourCartText = By.xpath("//div[@class='header_secondary_container']//span[.='Your Cart']");
    private By qtyText = By.xpath("//div[@class='cart_quantity_label']");
    private By descText = By.xpath("//div[@class='cart_desc_label']");
    private By continueShoppingBtn = By.id("continue-shopping");
    private By checkOutBtn = By.id("checkout");
    private By productItemOnCartPage = By.className("cart_item");
    private String productNameOnCartPageLocator = "(//div[@class='inventory_item_name'])[%d]";
    private String productDecOnCartPageLocator = "(//div[@class='inventory_item_desc'])[%d]";
    private String productPriceOnCartPageLocator = "(//div[@class='inventory_item_price'])[%d]";
    private By productItem = By.className("cart_item");
    private By removeButton = By.xpath("//button[.='Remove']");
    final private String QTY_LABEL = "QTY";
    final private String DESCRIPTION_LABEL = "DESCRIPTION";
    final private String CONTINUE_SHOPPING_LABEL = "Continue Shopping";
    final private String CHECKOUT_LABEL = "Checkout";
    final private String CHECKOUT_STEP1_LABEL = "https://www.saucedemo.com/checkout-step-one.html";
    final private String INVENTORY_URL = "https://www.saucedemo.com/inventory.html";


    public Cart(WebDriver driver) {
        this.driver = driver;
    }


    public boolean isYourCardTextDisplayedOnCartPage(){
        boolean result = false;
        if (driver.findElements(yourCartText).size() > 0){
            result = true;
        }
        return result;
    }

    public boolean isQtyTextDisplayedOnCartPage(){
        boolean result = false;
        By qtyElm = qtyText;
        if (driver.findElements(qtyElm).size() > 0){
            if (driver.findElement(qtyElm).getText().equalsIgnoreCase(QTY_LABEL)){
                result = true;
            }
        }
        return result;
    }

    public boolean isDescTextDisplayedOnCartPage(){
        boolean result = false;
        By descElm = descText;
        if (driver.findElements(descElm).size() > 0){
            if (driver.findElement(descElm).getText().equalsIgnoreCase(DESCRIPTION_LABEL)){
                result = true;
            }
        }
        return result;
    }
    public boolean isContinueShoppingBtnDisplayed(){
        boolean result = false;
        By continueShoppingBtnElm = continueShoppingBtn;
        if (driver.findElements(continueShoppingBtnElm).size() > 0){
            if (driver.findElement(continueShoppingBtnElm).getText().equalsIgnoreCase(CONTINUE_SHOPPING_LABEL)){
                result = true;
            }
        }
        return result;
    }

    public boolean isCheckOutBtnDisplayed(){
        boolean result = false;
        By checkOutBtnElm = checkOutBtn;
        if (driver.findElements(checkOutBtnElm).size() > 0){
            if (driver.findElement(checkOutBtnElm).getText().equalsIgnoreCase(CHECKOUT_LABEL)){
                result = true;
            }
        }
        return result;
    }

    public boolean isTotalProductInCartPageSameAsNumberOnCartIcon(){
        boolean result = false;
        navigation = new Navigation(driver);
        int numberOnCartIcon = navigation.countNumberOfProductOnCartIcon();
        int totalProductInCartPage = driver.findElements(productItemOnCartPage).size();

        if (numberOnCartIcon == totalProductInCartPage){
            result = true;
        }

        return result;
    }
    public String getProductNameOnCartPage(int index){
        return driver.findElement(By.xpath(String.format(productNameOnCartPageLocator, index))).getText();
    }

    public String getProductDescOnCartPage(int index){
        return driver.findElement(By.xpath(String.format(productDecOnCartPageLocator, index))).getText();
    }

    public String getProductPriceOnCartPage(int index){
        String productPriceCurrency = driver.findElement(By.xpath(String.format(productPriceOnCartPageLocator, index))).getText();
        return productPriceCurrency.replace("$","");
    }

    public int getTotalProductOnCartPage(){
        return driver.findElements(productItem).size();
    }

    public Product getInfoOfEachProductOnCartPage(int index){
        Product eachProductOnCart;
        String productName = getProductNameOnCartPage(index);
        String productDesc = getProductDescOnCartPage(index);
        String productPrice = getProductPriceOnCartPage(index);
        eachProductOnCart = new Product(productName, productDesc,Double.valueOf(productPrice) );

        return eachProductOnCart;
    }

    public List<Product> getInfoOfAllProductOnCartPage(int totalProduct){
        List<Product> infoOfAllProductsOnCartPage = new ArrayList<>();
        Product infoOfEachProduct;

        for (int i=0; i<totalProduct; i++){
            infoOfEachProduct = getInfoOfEachProductOnCartPage(i+1);
            infoOfAllProductsOnCartPage.add(infoOfEachProduct);
        }

        return infoOfAllProductsOnCartPage;
    }

    public boolean isInfoProductOnCartPageSameAsInfoAddedProductFromProductList(List<Product> listOfProductInfoOnProductListPage){
        boolean result = true;
        int totalCartItem = getTotalProductOnCartPage();

        List<Product> infoOfProductOnCartPage = getInfoOfAllProductOnCartPage(totalCartItem);

        for (int i=0; i<totalCartItem;i++){
            if (!listOfProductInfoOnProductListPage.get(i).getProductName().equalsIgnoreCase(infoOfProductOnCartPage.get(i).getProductName()) ||
                    !listOfProductInfoOnProductListPage.get(i).getProductDescription().equalsIgnoreCase(infoOfProductOnCartPage.get(i).getProductDescription()) ||
                    listOfProductInfoOnProductListPage.get(i).getProductPrice()!=infoOfProductOnCartPage.get(i).getProductPrice()){
                result = false;
            }
        }

        return result;
    }

    public void clickOnContinueShoppingButton(){
        waitElementClickAble(driver.findElement(continueShoppingBtn)).click();
    }

    public boolean isUrlAfterClickingOnContinueShoppingBtnCorrect(){
        boolean result = false;
        if (driver.getCurrentUrl().equalsIgnoreCase(INVENTORY_URL)){
            result = true;
        }
        return result;
    }

    public void clickOnCheckoutButton(){
        waitElementClickAble(driver.findElement(checkOutBtn)).click();
    }

    public boolean isUrlAfterClickingOnCheckoutBtnCorrect(){
        boolean result = false;
        if (driver.getCurrentUrl().equalsIgnoreCase(CHECKOUT_STEP1_LABEL)){
            result = true;
        }
        return result;
    }

    public void clickOnRemoveBtn(){
        waitElementClickAble(driver.findElement(removeButton)).click();
    }

    public void clickOnProductNameOnCartPage(int index){
        waitElementClickAble(driver.findElement(By.xpath(String.format(productNameOnCartPageLocator,index)))).click();
    }

}
