package tests;

import com.codeborne.selenide.Configuration;
import config.browser.BrowserConfig;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    public static final BrowserConfig BROWSER = BrowserConfig.getInstance();

    @BeforeAll
    static void setUp(){
        Configuration.browser = BROWSER.browser();
        Configuration.pageLoadStrategy = BROWSER.pageLoadStrategy();
        Configuration.headless = Boolean.parseBoolean(BROWSER.headless());
    }

}
