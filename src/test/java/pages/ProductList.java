package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static common.Utils.*;

public class ProductList {
    WebDriver driver;
    private By listProductItems = By.xpath("//div[@class='inventory_list']/div[@class='inventory_item']");
    private String productImageLocator = "(//div[@class='inventory_item_img'])[%d]/a/img";
    private String productNameLocator = "(//div[@class='inventory_item_name'])[%d]";
    private String productDescriptionLocator = "(//div[@class='inventory_item_desc'])[%d]";
    private String productPricePercentLocator = "(//div[@class='inventory_item_price'])[%d]";

    final String PRODUCT_FILE_NAME = "src/test/resources/Saucedemo-ProductList.xlsx";
    private String addToCartButtonLocator = "(//button[@class='btn btn_primary btn_small btn_inventory'])[%d]";
    private String removeProductButtonLocator = "(//button[@class='btn btn_secondary btn_small btn_inventory'])[%d]";
    private By removeProductButton = By.xpath("//button[@class='btn btn_secondary btn_small btn_inventory']");
    final private List<String> EXPECTED_SORT_OPTIONS = Arrays.asList("Name (Z to A)","Name (A to Z)", "Price (low to high)", "Price (high to low)");
    private By sortDropDown = By.className("product_sort_container");
    private String addToCartBtnStateLabelLocator = "(//div[@class='pricebar']/button)[%d]";



    public ProductList(WebDriver driver) {
        this.driver = driver;
    }

    public ProductList() {
    }

    public int countTotalNumberOfProductList(){
        return driver.findElements(listProductItems).size();
    }

    public String getProductImageOnPlp(int index){
        return driver.findElement(By.xpath(String.format(productImageLocator,index))).getAttribute("src").trim();
    }

    public String getProductNameOnPlp(int index){
        return driver.findElement(By.xpath(String.format(productNameLocator,index))).getText().trim();
    }

    public List<String> getProductNameListOnPLP(){
        int number = countTotalNumberOfProductList();
        List<String> productNameList = new ArrayList<>();
        for (int i = 0; i <number; i++){
            productNameList.add(getProductNameOnPlp(i+1));
        }
        return productNameList;
    }

    public String getProductDescriptionOnPlp(int index){
        return driver.findElement(By.xpath(String.format(productDescriptionLocator,index))).getText().trim();
    }

    public double getProductPriceOnPlp(int index){
        String productPricePercent = driver.findElement(By.xpath(String.format(productPricePercentLocator,index))).getText().trim();
        return Double.parseDouble(productPricePercent.replace("$",""));
    }
    public List<Double> getProductPriceListOnPlp(){
        int number = countTotalNumberOfProductList();
        List<Double> productPriceList = new ArrayList<>();
        for (int i = 0; i <number; i++){
            productPriceList.add(getProductPriceOnPlp(i+1));
        }
        return productPriceList;
    }

    public String getProductDetailUrl(){
        return driver.getCurrentUrl();
    }

    public Product getInfoEachProductOnWeb(int index ){
        Product eachProductList = new Product();
        String productImage = getProductImageOnPlp(index);

        String productName = getProductNameOnPlp(index);

        String productDescription = getProductDescriptionOnPlp(index);

        Double productPrice = getProductPriceOnPlp(index);

        eachProductList = new Product(productImage, productName,productDescription, productPrice);
        return eachProductList;
    }

    public List<Product> getListAllProductInExelFile(String productFileName){
        return readFile(PRODUCT_FILE_NAME);
    }

    public boolean isInfoOfProductOnWebSameAsTheExelFile(){
        boolean result = false;

        List<Product> allProducts = getListAllProductInExelFile(PRODUCT_FILE_NAME);

        if (allProducts.size() != countTotalNumberOfProductList()){
            return false;
        }

        for (int i = 0; i < countTotalNumberOfProductList(); i++){
            Product eachProductOnWeb = getInfoEachProductOnWeb(i+1);
            if (allProducts.get(i).getProductImage().trim().equalsIgnoreCase(eachProductOnWeb.getProductImage()) &&
                    allProducts.get(i).getProductName().trim().equalsIgnoreCase(eachProductOnWeb.getProductName()) &&
                    allProducts.get(i).getProductDescription().trim().equalsIgnoreCase(eachProductOnWeb.getProductDescription()) &&
                    allProducts.get(i).getProductPrice() == eachProductOnWeb.getProductPrice()){
                result = true;
            }
        }
        return result;
    }


    public void clickOnAddToCartButton(int index){
        waitElementClickAble(driver.findElement(By.xpath(String.format(addToCartButtonLocator, index)))).click();
    }

    public void clickOnRemoveFromCartButton(int index){
        waitElementClickAble(driver.findElement(By.xpath(String.format(removeProductButtonLocator, index)))).click();
    }

    public void clickOnProductNameOnProductListPage(int index){
        waitElementClickAble(driver.findElement(By.xpath(String.format(productNameLocator,index)))).click();
    }

    public Select sortDropDown(){
        Select select = new Select(driver.findElement(sortDropDown));
        return select;
    }

    public List<String> getAllOptionOfSortDropdown(){
        Select select = sortDropDown();
        List<WebElement> sortOptionsWebElm = select.getOptions();
        List<String> sortOptionText = new ArrayList<>();
        for (WebElement sortOption:sortOptionsWebElm){
            if (sortOption.getAttribute("value")!=" "){
                sortOptionText.add(sortOption.getText());
            }
        }
        return sortOptionText;
    }

    public boolean isSortDropDownOptionCorrect(){
        List<String> sortOptionText = getAllOptionOfSortDropdown();
        return are2StringArrayListEqualIgnoreOrder(EXPECTED_SORT_OPTIONS, sortOptionText);
    }

    public void selectSortDropdownByValue(String value){
        Select select = sortDropDown();
        select.selectByValue(value);
    }

    public List<String> getInfoOfProductOnPlp(int index){
        List<String>  productInfo;
        String productImage = getProductImageOnPlp(index);
        String productName = getProductNameOnPlp(index);
        String productDes = getProductDescriptionOnPlp((index));
        String productPrice = String.valueOf(getProductPriceOnPlp(index));
        String addToCartBtnStateLabel = driver.findElement(By.xpath(String.format(addToCartBtnStateLabelLocator,index))).getText();
        productInfo = new LinkedList<>(Arrays.asList(productImage,productName,productDes,productPrice,addToCartBtnStateLabel ));

        return productInfo;
    }

    public List<String> getInfoOfListProductOnPlp(int totalProduct){
        List<String> productInfo;
        List<String> listOfProductInfo = new ArrayList<>();
        for (int i = 0; i<totalProduct; i++){
            productInfo = getInfoOfProductOnPlp(i);
            listOfProductInfo.add(productInfo.toString());
        }
        return listOfProductInfo;
    }

    public Product getInfoOfEachProductOnProductListPage(int index){
        Product eachProductOnPlp;
        String productName = getProductNameOnPlp(index);
        String productDesc = getProductDescriptionOnPlp(index);
        String productPrice = String.valueOf(getProductPriceOnPlp(index));
        eachProductOnPlp = new Product(productName, productDesc,Double.valueOf(productPrice) );

        return eachProductOnPlp;
    }

    public List<Product> getInfoOfAllProductOnProductListPage(int totalProduct){
        List<Product> infoOfAllProductsOnPlp = new ArrayList<>();
        Product infoOfEachProduct;

        for (int i=0; i<totalProduct; i++){
            infoOfEachProduct = getInfoOfEachProductOnProductListPage(i+1);
            infoOfAllProductsOnPlp.add(infoOfEachProduct);
        }

        return infoOfAllProductsOnPlp;
    }

    public boolean isProductInfoInProductDetailSameAsProductList(List<String> productInfoOnPlp, List<String> productInfoOnPdp){
        boolean result = false;
        if (are2StringArrayListEqualIncludeOrder(productInfoOnPlp,productInfoOnPdp)){
            result = true;
        }

        return result;
    }

    public void clickOnRemoveButtonOnAllProductsOfProductList(int totalProduct){
        for (int i = 0; i< totalProduct; i++){
            clickOnRemoveFromCartButton(1);
        }
    }

    public int getNumberOfProductHasRemoveButton(){
        return driver.findElements(removeProductButton).size();
    }

    public void clickOnAddToCartButtonOnAllProductsOfProductList(int totalProduct){
        for (int i = 0; i< totalProduct; i++){
            clickOnAddToCartButton(1);
        }
    }



}
