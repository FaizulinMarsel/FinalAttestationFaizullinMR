package pages;

import config.expected.Expected;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.component.Title.EXPECTED_DATA;

public class CartPage extends BasePage{
    private final By yourCart = By.className("title");
    private final By checkout = By.id("checkout");

    @Step("Проверка, что страница корзины открылась")
    public CartPage checkOpenPageCart(){
        assertEquals(EXPECTED_DATA.getTextCartPage(), getText(yourCart));
        return this;
    }
    @Step("Подтверждение товаров в корзине")
    public CartPage checkout(){
        click(checkout);
        return this;
    }
}
