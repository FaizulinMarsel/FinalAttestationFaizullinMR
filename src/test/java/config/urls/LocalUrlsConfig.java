package config.urls;

import config.ProjectConfig;

import java.util.Properties;

public enum LocalUrlsConfig implements UrlsConfig{

    URLS_CONFIG;
    private static final Properties PROPS = ProjectConfig.readProperties(ProjectConfig.Env.URLS);


    @Override
    public String UrlUiSaucedemo() {
        return PROPS.getProperty("urlSaucedemo");
    }

    @Override
    public String UrlApiXclient() {
        return PROPS.getProperty("urlXclient");
    }
}
