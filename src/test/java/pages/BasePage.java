package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage {
    protected void type(By locator, String text){
        $(locator).shouldBe(visible).setValue(text);
    }
    protected void click(By locator){
        $(locator).shouldBe(enabled).click();
    }
    protected String getText(By locator){
        return $(locator).shouldBe(enabled).getText();
    }

}
