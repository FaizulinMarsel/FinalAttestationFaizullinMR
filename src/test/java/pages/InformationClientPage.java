package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.FakeTestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.component.Title.EXPECTED_DATA;

public class InformationClientPage extends BasePage{
    private final By checkoutYourInformationText = By.className("title");
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");

    @Step("Проверка, что страница с информацией о клиенте открылась")
    public InformationClientPage checkOpenPageInformationClient(){
        assertEquals(EXPECTED_DATA.getTextInformationClientPage(), getText(checkoutYourInformationText));
        return this;
    }
    @Step("Ввод информации о клиенте")
    public InformationClientPage addInformationClient(){
        type(firstName, FakeTestData.firstName);
        type(lastName, FakeTestData.lastName);
        type(postalCode, FakeTestData.postalCode);
        return this;
    }
    @Step("Продолжить оформление покупки")
    public InformationClientPage continueCheckout(){
        click(continueButton);
        return this;
    }
}
