package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Footer {
    WebDriver driver;

    private By twitterIconHref = By.xpath("//li[@class='social_twitter']/a");
    private By faceBookIconHref = By.xpath("//li[@class='social_facebook']/a");
    private By linkedInIconHref = By.xpath("//li[@class='social_linkedin']/a");
    private By footerImage = By.xpath("//footer[@class='footer']/img");
    private By copyWriting = By.className("footer_copy");
    final private String TWITTER_URL = "https://twitter.com/saucelabs";
    final private String FACEBOOK_URL = "https://www.facebook.com/saucelabs";
    final private String LINKED_IN_URL = "https://www.linkedin.com/company/sauce-labs/";
    final private String COPY_WRITING_TEXT = "© 2023 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy";
    final private String FOOTER_IMAGE_URL = "https://www.saucedemo.com/static/media/SwagBot_Footer_graphic.2e87acec.png";

    public Footer(WebDriver driver) {
        this.driver = driver;
    }

    public Footer() {
    }

    public String getTwitterUrl(){
        return driver.findElement(twitterIconHref).getAttribute("href");
    }
    public boolean isTwitterUrlCorrect(){
        boolean result = false;
        if (getTwitterUrl().equalsIgnoreCase(TWITTER_URL)){
            result = true;
        }

        return result;
    }

    public String getFaceBookUrl(){
        return driver.findElement(faceBookIconHref).getAttribute("href");
    }
    public boolean isFaceBookUrlCorrect(){
        boolean result = false;
        if (getFaceBookUrl().equalsIgnoreCase(FACEBOOK_URL)){
            result = true;
        }

        return result;
    }

    public String getLinkedInUrl(){
        return driver.findElement(linkedInIconHref).getAttribute("href");
    }
    public boolean isLinkedInUrlCorrect(){
        boolean result = false;
        if (getLinkedInUrl().equalsIgnoreCase(LINKED_IN_URL)){
            result = true;
        }

        return result;
    }

    public String getCopyWritingText(){
        return driver.findElement(copyWriting).getText();
    }

    public boolean isCopyWritingCorrect(){
        boolean result = false;
        if (getCopyWritingText().equalsIgnoreCase(COPY_WRITING_TEXT)){
            result = true;
        }
        return result;
    }

    public String getFooterImageUrl(){
        return driver.findElement(footerImage).getAttribute("src");
    }

    public boolean isFooterImageCorrect(){
        boolean result = false;
        if (getFooterImageUrl().equalsIgnoreCase(FOOTER_IMAGE_URL)){
            result = true;
        }
        return result;
    }
}
