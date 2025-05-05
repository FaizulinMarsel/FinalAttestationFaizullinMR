package tests.ui.saucedemo;

import io.qameta.allure.Description;
import io.qameta.allure.Param;
import jupiter.arguments.ui.TestUsersUiDataProvider;
import model.TestUserUi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import pages.*;
import tests.BaseTest;

import static io.qameta.allure.model.Parameter.Mode.HIDDEN;

public class ShoppingCartTest extends BaseTest {

    @ParameterizedTest
    @ArgumentsSource(TestUsersUiDataProvider.class)
    @DisplayName("Проверка итоговой суммы корзины")
    @Description ("Добавляем три товара в корзину и сравниваем их сумму + налог с итоговой суммой корзины" +
            "под разными пользователями")
    public void compareTotalSumAndSumProductsWithTax(@Param(mode = HIDDEN) TestUserUi user){
        new MainPage()
                .openPage();
        new LoginPage()
                .loginAs(user);
        new ProductsPage()
                .checkOpenPageProducts()
                .addProductCart()
                .goToCart();
        new CartPage()
                .checkOpenPageCart()
                .checkout();
        new InformationClientPage()
                .checkOpenPageInformationClient()
                .addInformationClient()
                .continueCheckout();
        new CheckoutPage()
                .checkOpenCheckoutPage()
                .findTotalPrice()
                .clickFinishButton();
    }
}
