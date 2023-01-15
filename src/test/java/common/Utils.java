package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {


    private static WebDriver driver;
    final private static int TIMEOUT = 20;
    public static WebDriverWait wait;

    public static WebDriver browser(String browserName){
        switch (browserName.toLowerCase()){
            case "firefox":{
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            default:{
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
        }
        wait = new WebDriverWait(driver,Duration.ofSeconds(TIMEOUT));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIMEOUT));
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public static void threadSleep(int sleepTime){
        try{
            Thread.sleep(sleepTime);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static WebElement waitElementClickAble(WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitElementDisplayed(By element){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static List<Product> readFile(String fileName){
        List<Product> productList = new ArrayList<>();
        try {
            //Get file
            FileInputStream excelFile = new FileInputStream(new File(fileName));
            //Get workbook
            Workbook workbook = new XSSFWorkbook(excelFile);
            //Get sheet
            Sheet datatypeSheet = workbook.getSheetAt(0);
            //Get all rows
            Iterator<Row> iterator = datatypeSheet.iterator();
            //Move to row that is header
            iterator.next();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();

                //Read cell and set value for product object
                Product product = new Product();

                product.setProductName(currentRow.getCell(0).getStringCellValue());
                product.setProductDescription(currentRow.getCell(1).getStringCellValue());
                product.setProductPrice(Double.valueOf(currentRow.getCell(2).getNumericCellValue()));
                product.setProductImage(currentRow.getCell(3).getStringCellValue());
                product.setProductDetailUrl(currentRow.getCell(4).getStringCellValue());

                productList.add(product);
            }

            workbook.close();
            excelFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;

    }

    public static boolean are2StringArrayListEqualIgnoreOrder(List<String> list1, List<String>  list2){
        boolean result = false;
        if (list1.size() != list2.size()){
            return false;
        }
        List<String> sortedList1 = list1.stream().sorted().collect(Collectors.toList());
        List<String> sortedList2 = list2.stream().sorted().collect(Collectors.toList());

        for (int i = 0; i<sortedList1.size(); i++){
            if (!sortedList1.get(i).equalsIgnoreCase(sortedList2.get(i))){
                return false;
            }else{
                result = true;
            }
        }
        return result;
    }

    public static boolean are2StringArrayListEqualIncludeOrder(List<String> list1, List<String> list2){
        boolean result = false;
        if (list1.size() != list2.size()){
            return false;
        }

        for (int i = 0; i<list1.size(); i++){
            if (!list1.get(i).equalsIgnoreCase(list2.get(i))){
                return false;
            }else{
                result = true;
            }
        }
        return result;
    }

    public static boolean are2DoubleArrayListEqualIncludeOrder(List<Double> list1, List<Double>  list2){
        boolean result = false;
        if (list1.size() != list2.size()){
            return false;
        }

        for (int i = 0; i<list1.size(); i++){
            if (!list1.get(i).equals(list2.get(i))){
                return false;
            }else{
                result = true;
            }
        }
        return result;
    }

    public static void removeItemOfList(List<String> list){
        list.removeAll(list);
    }

    public static void takeScreenShot(String fileWithPath) throws IOException {
        //Convert web driver object to TakeScreenshot
        TakesScreenshot srcShot = ((TakesScreenshot) driver);

        //Call getScreenshotAs method to create image file
        File srcFile = srcShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(srcFile, DestFile);

    }



}
