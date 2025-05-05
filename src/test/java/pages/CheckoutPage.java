package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import utils.SumUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.component.Title.EXPECTED_DATA;

public class CheckoutPage extends BasePage{
    private final By finishButton = By.id("finish");
    private final By checkoutOverviewText = By.className("title");
    private final By subTotalSum = By.className("summary_subtotal_label");
    private final By taxSum = By.className("summary_tax_label");
    private final By totalSum = By.className("summary_total_label");
    @Step("Проверка, что страница с итоговой суммой товаров открылась")
    public CheckoutPage checkOpenCheckoutPage(){
        assertEquals(EXPECTED_DATA.getTextCheckoutOverviewPage(), getText(checkoutOverviewText));
        return this;
    }
    @Step("Сравнение итоговой суммы товаров")
    public CheckoutPage findTotalPrice(){
        BigDecimal subTotal = SumUtils.toDecimal(getText(subTotalSum).replace(EXPECTED_DATA.getTextSubtotalSum(), ""));
        BigDecimal tax = SumUtils.toDecimal(getText(taxSum).replace(EXPECTED_DATA.getTextTaxSum(),""));
        BigDecimal total = SumUtils.toDecimal(getText(totalSum).replace(EXPECTED_DATA.getTextTotalSum(), ""));
        Assertions.assertEquals(subTotal.add(tax), total);
        return this;
    }
    @Step("Завершение оформления заказа")
    public CheckoutPage clickFinishButton(){
        click(finishButton);
        return this;
    }
}
