package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.component.Title.EXPECTED_DATA;

public class ProductsPage extends BasePage{
    private final By products = By.className("title");
    private final By firstElement = By.name("add-to-cart-sauce-labs-backpack");
    private final By secondElement = By.name("add-to-cart-sauce-labs-bolt-t-shirt");
    private final By thirdElement = By.name("add-to-cart-sauce-labs-onesie");
    private final By shoppingCartButton = By.className("shopping_cart_link");

    @Step("Проверка, что страница продуктов открылась")
    public ProductsPage checkOpenPageProducts(){
        assertEquals(EXPECTED_DATA.getTextProductsPageTitle(), getText(products));
        return this;
    }
    @Step("Добавление продуктов в корзину")
    public ProductsPage addProductCart(){
        click(firstElement);
        click(secondElement);
        click(thirdElement);
        return this;
    }
    @Step("Перейти в корзину")
    public ProductsPage goToCart(){
        click(shoppingCartButton);
        return this;
    }

}
