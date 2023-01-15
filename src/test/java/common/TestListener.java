package common;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import static common.Utils.getDriver;


public class TestListener implements ITestListener {
    @Override
    public void onStart(ITestContext result){
        Log.info(result.getName() + " is started");
    }

    @Override
    public void onFinish(ITestContext result){
        Log.info(result.getName() + " is finished");
    }

    @Override
    public void onTestStart(ITestResult result){
        Log.info(result.getName() + " is stared");
    }

    @Override
    public void onTestSuccess(ITestResult result){
        Log.info(result.getName() + " is successful");
    }

    @Override
    public void onTestFailure(ITestResult result){

        Log.error(result.getName() + " is failed");

        Allure.addAttachment(UUID.randomUUID().toString(), new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
//        try {
//            takeScreenShot("src/test/resources/test.png");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onTestSkipped(ITestResult result){
        Log.info(result.getName() + " is skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result){
        Log.error(result.getName() + " is fail but within Success Percentage: ");
    }


}
