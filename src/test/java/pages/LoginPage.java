package pages;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import model.TestUserUi;
import org.openqa.selenium.By;

import static io.qameta.allure.model.Parameter.Mode.HIDDEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage extends BasePage{
    private final By userName = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorText = By.cssSelector("h3[data-test='error']");

    @Step("Авторизоваться в системе как {user.username}")
    public LoginPage loginAs(@Param(name = "user", mode = HIDDEN) TestUserUi user) {
        type(userName, user.username());
        type(password, user.password());
        click(loginButton);
        return this;
    }
    @Step("Проверка, что появилась ошибка при входе")
    public LoginPage checkErrorText(String errorTxt){
        assertEquals(errorTxt, getText(errorText));
        return this;
    }
}
