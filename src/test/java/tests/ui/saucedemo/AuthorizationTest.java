package tests.ui.saucedemo;

import config.expected.Expected;
import io.qameta.allure.Param;
import jupiter.annotations.ui.WithTestUserUi;
import model.TestUserUi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.ProductsPage;
import tests.BaseTest;

import static io.qameta.allure.model.Parameter.Mode.HIDDEN;
import static jupiter.annotations.ui.WithTestUserUi.Type.LockedOut;
import static jupiter.annotations.ui.WithTestUserUi.Type.Standard;

public class AuthorizationTest extends BaseTest {

    @Test
    @DisplayName("Авторизация с не заблокированным пользователем")
    @WithTestUserUi(type = Standard)
    public void correctAuthorization(@Param(mode = HIDDEN) TestUserUi user) {
        new MainPage()
                .openPage()
                .checkOpenMainPage();
        new LoginPage()
                .loginAs(user);
        new ProductsPage()
                .checkOpenPageProducts();
    }
    @Test
    @DisplayName("Авторизация с заблокированным пользователем")
    @WithTestUserUi(type = LockedOut)
    public void inCorrectAuthorization(@Param(mode = HIDDEN) TestUserUi user) {
        new MainPage()
                .openPage()
                .checkOpenMainPage();
        new LoginPage()
                .loginAs(user)
                .checkErrorText(Expected.getInstance().getLockedUserErrorText());
    }
}
