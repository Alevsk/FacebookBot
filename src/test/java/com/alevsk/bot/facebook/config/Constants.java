package com.alevsk.bot.facebook.config;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Created by lehuerta on 07/04/2016.
 */
public interface Constants {

    //FRAMEWORK CONVENTIONS
    public String ID = "id";
    public String XPATH = "xpath";
    public String CSS = "css";
    public String TAG = "tag";

    //FB LOGIN PAGE
    public String FB_URL = "http://www.facebook.com";
    public String FB_LOGIN_FORM_ID = "login_form";
    public String FB_LOGIN_USERNAME_ID = "email";
    public String FB_LOGIN_PASSWORD_ID = "pass";
    public String FB_LOGIN_BUTTON_ID = "u_0_y";

    //FB CONVERSATION PAGE
    public String FB_CONVERSATION_URL = "https://www.facebook.com/messages/conversation-160363887660672";
    //public String FB_CONVERSATION_URL = "https://www.facebook.com/messages/conversation-389162044625051";
    //public String FB_CONVERSATION_URL = "https://www.facebook.com/messages/100001868172706";
    public String FB_CONVERSATION_NAME = "Lenin Alevski HA";
    public String FB_CONVERSATION_TEXTAREA_XPATH = "//*[@id=\"js_q\"]/div[1]/div[2]/textarea";
    public String FB_SINGLE_CONVERSATION_TEXTAREA_XPATH = "//*[@id=\"js_p\"]/div[1]/div[2]/textarea";
    public String FB_CONVERSATION_CONTAINER_ID = "webMessengerRecentMessages";
    public String FB_CONVERSATION_ITEM_CLASS = ".webMessengerMessageGroup.clearfix";
}
