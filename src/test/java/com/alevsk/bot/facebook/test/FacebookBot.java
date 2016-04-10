package com.alevsk.bot.facebook.test;

import com.alevsk.bot.facebook.config.Constants;
import com.alevsk.bot.facebook.util.FacebookMessage;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by lehuerta on 07/04/2016.
 */
public class FacebookBot {

    private WebDriver driver;
    private WebDriverWait wait;
    private String username;
    private String password;

    @Before
    public void setup(){

        try {
            //horrible solution but I dont want to have my facebook credentials on a github repository
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\lehuerta\\IdeaProjects\\FacebookBot\\credentials.txt"));
            username = br.readLine();
            password = br.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lehuerta\\IdeaProjects\\FacebookBot\\Driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized -incognito");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 60);
    }

    public void waitForVisibility(String by, String identifier) {
        switch (by) {
            case Constants.ID:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(identifier)));
                break;

            case Constants.XPATH:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identifier)));
                break;

            case Constants.CSS:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(identifier)));
                break;

            case Constants.TAG:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(identifier)));
                break;

            default:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(identifier)));
                break;
        }
    }

    public List<WebElement> getElements(String by, String identifier) {
        List<WebElement> elements = null;
        switch (by) {
            case Constants.ID:
                elements = driver.findElements(By.id(identifier));
                break;

            case Constants.XPATH:
                elements = driver.findElements(By.xpath(identifier));
                break;

            case Constants.CSS:
                elements = driver.findElements(By.cssSelector(identifier));
                break;

            case Constants.TAG:
                elements = driver.findElements(By.tagName(identifier));
                break;

            default:
                elements = driver.findElements(By.id(identifier));
                break;
        }
        return elements;
    }

    public WebElement getElement(String by, String identifier) {
        WebElement element = null;
        switch (by) {
            case Constants.ID:
                element = driver.findElement(By.id(identifier));
                break;

            case Constants.XPATH:
                element = driver.findElement(By.xpath(identifier));
                break;

            case Constants.CSS:
                element = driver.findElement(By.cssSelector(identifier));
                break;

            case Constants.TAG:
                element = driver.findElement(By.tagName(identifier));
                break;

            default:
                element = driver.findElement(By.id(identifier));
                break;
        }
        return element;
    }

    @Test
    public void openFacebook() {
        driver.navigate().to(Constants.FB_URL);
        facebookLoginForm();
        //facebookConversationSpammer(Constants.FB_CONVERSATION_URL,"eaeaeae",50);
        facebookGroupChatRepeater(Constants.FB_CONVERSATION_URL);
        //facebookSingleChatRepeater(Constants.FB_CONVERSATION_URL);
    }

    public void facebookLoginForm() {
        waitForVisibility(Constants.ID,Constants.FB_LOGIN_FORM_ID);
        getElement(Constants.ID,Constants.FB_LOGIN_USERNAME_ID).sendKeys(this.username);
        getElement(Constants.ID,Constants.FB_LOGIN_PASSWORD_ID).sendKeys(this.password);
        getElement(Constants.ID,Constants.FB_LOGIN_BUTTON_ID).click();
    }

    public void facebookConversationSpammer(String conversationUrl, String text, int n) {
        driver.navigate().to(conversationUrl);
        waitForVisibility(Constants.XPATH,Constants.FB_CONVERSATION_TEXTAREA_XPATH);

        for (int i = 0; i < n; i++) {
            getElement(Constants.XPATH,Constants.FB_CONVERSATION_TEXTAREA_XPATH).sendKeys(text);
            getElement(Constants.XPATH,Constants.FB_CONVERSATION_TEXTAREA_XPATH).sendKeys(Keys.RETURN);
        }
    }

    public void facebookSingleChatRepeater(String conversationUrl) {
        driver.navigate().to(conversationUrl);
        waitForVisibility(Constants.ID, Constants.FB_CONVERSATION_CONTAINER_ID);
        waitForVisibility(Constants.XPATH, Constants.FB_SINGLE_CONVERSATION_TEXTAREA_XPATH);
        String lastMessageReceivedHash = "";

        while (true) {

            try {

                List<WebElement> items = getElements(Constants.CSS, Constants.FB_CONVERSATION_ITEM_CLASS);
                WebElement comment = items.get(items.size() - 1);
                String messageString = comment.getText();
                String[] messageArray = comment.getText().split("\n");
                String time = messageArray[0];
                String name = messageArray[1];
                String text = "";

                for (int k = 2; k < messageArray.length; k++)
                    text += messageArray[k] + " ";

                String hash = new String(Base64.encodeBase64(messageString.getBytes()));
                FacebookMessage message = new FacebookMessage(hash, time, name, text);

                if (!lastMessageReceivedHash.equals(hash) && !name.equals(Constants.FB_CONVERSATION_NAME)) {
                    System.out.println("Reading last message ...");
                    System.out.println(text);
                    System.out.println("-------------------------");
                    lastMessageReceivedHash = hash;

                    getElement(Constants.XPATH, Constants.FB_SINGLE_CONVERSATION_TEXTAREA_XPATH).sendKeys(text);
                    getElement(Constants.XPATH, Constants.FB_SINGLE_CONVERSATION_TEXTAREA_XPATH).sendKeys(Keys.RETURN);
                }
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {

            }
        }
    }

    public void facebookGroupChatRepeater(String conversationUrl) {
        driver.navigate().to(conversationUrl);
        waitForVisibility(Constants.ID,Constants.FB_CONVERSATION_CONTAINER_ID);
        waitForVisibility(Constants.XPATH,Constants.FB_CONVERSATION_TEXTAREA_XPATH);
        String lastMessageReceivedHash = "";

        while(true) {

            try{

                List<WebElement> items = getElements(Constants.CSS,Constants.FB_CONVERSATION_ITEM_CLASS);
                WebElement comment = items.get(items.size() - 1);
                String messageString = comment.getText();
                String[] messageArray = comment.getText().split("\n");
                String time = messageArray[0];
                String name = messageArray[1];
                String text = "";

                for (int k = 2; k < messageArray.length; k++)
                    text += messageArray[k] + " ";

                String hash = new String(Base64.encodeBase64(messageString.getBytes()));
                FacebookMessage message = new FacebookMessage(hash,time,name,text);

                if(!lastMessageReceivedHash.equals(hash) && !name.equals(Constants.FB_CONVERSATION_NAME)) {
                    System.out.println("Reading last message ...");
                    System.out.println(text);
                    System.out.println("-------------------------");
                    lastMessageReceivedHash = hash;

                    getElement(Constants.XPATH,Constants.FB_CONVERSATION_TEXTAREA_XPATH).sendKeys(text);
                    getElement(Constants.XPATH,Constants.FB_CONVERSATION_TEXTAREA_XPATH).sendKeys(Keys.RETURN);
                }
            }
            catch(org.openqa.selenium.StaleElementReferenceException ex)
            {

            }
        }
    }
}
