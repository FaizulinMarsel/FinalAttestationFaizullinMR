package config.browser;

import config.ProjectConfig;

import java.util.Properties;

public enum LocalBrowserConfig implements BrowserConfig {
    BROWSER_CONFIG;
    private static final Properties PROPS = ProjectConfig.readProperties(ProjectConfig.Env.BROWSER);

    @Override
    public String pageLoadStrategy() {
        return PROPS.getProperty("pageLoadStrategy","eager");
    }

    @Override
    public String browser() {
        return PROPS.getProperty("browser","chrome");
    }

    @Override
    public String headless() {
        return PROPS.getProperty("headless", "false");
    }

}
