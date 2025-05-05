package config.urls;

public interface UrlsConfig {

    static UrlsConfig getInstance(){
        return LocalUrlsConfig.URLS_CONFIG;
    }
    String UrlUiSaucedemo();
    String UrlApiXclient();
}
