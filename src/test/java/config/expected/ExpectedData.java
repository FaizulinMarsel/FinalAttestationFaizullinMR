package config.expected;

import config.ProjectConfig;

import java.util.Properties;

public enum ExpectedData implements Expected{
    EXPECTED_DATA;
    private static final Properties PROPS = ProjectConfig.readProperties(ProjectConfig.Env.ENV);

    @Override
    public String getLockedUserErrorText() {
        return PROPS.getProperty("lockedUserErrorText");
    }

    @Override
    public String getTextProductsPageTitle() {
        return PROPS.getProperty("productsPageTitle");
    }

    @Override
    public String getTextCartPage() {
        return PROPS.getProperty("cart");
    }

    @Override
    public String getTextInformationClientPage() {
        return PROPS.getProperty("checkoutYourInformationText");
    }

    @Override
    public String getTextCheckoutOverviewPage() {
        return PROPS.getProperty("checkoutOverview");
    }

    @Override
    public String getTextTaxSum() {
        return PROPS.getProperty("textTaxSum");
    }

    @Override
    public String getTextTotalSum() {
        return PROPS.getProperty("textTotalSum");
    }

    @Override
    public String getTextSubtotalSum() {
        return PROPS.getProperty("textSubtotalSum");
    }

    @Override
    public String getTextSwagLabs() {
        return PROPS.getProperty("textSwagLabs");
    }


}
