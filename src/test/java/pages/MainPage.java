package pages;

import config.urls.UrlsConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.component.Title.EXPECTED_DATA;

public class MainPage extends BasePage{

    private static final String URL = UrlsConfig.getInstance().UrlUiSaucedemo();
    private final By textSwagLabs = By.className("login_logo");
    @Step("Открыть главную страницу")
    public MainPage openPage(){
        open(URL);
        return this;
    }
    @Step("Проверка, что главная страница открылась")
    public MainPage checkOpenMainPage(){
        assertEquals(EXPECTED_DATA.getTextSwagLabs(), getText(textSwagLabs));
        return this;
    }
}
