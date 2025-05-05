package config.expected;

public interface Expected {
    static Expected getInstance(){
        return ExpectedData.EXPECTED_DATA;
    }
    String getLockedUserErrorText();
    String getTextProductsPageTitle();
    String getTextCartPage();
    String getTextInformationClientPage();
    String getTextCheckoutOverviewPage();
    String getTextTaxSum();
    String getTextTotalSum();
    String getTextSubtotalSum();
    String getTextSwagLabs();
}
