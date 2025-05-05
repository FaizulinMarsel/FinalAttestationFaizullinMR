package config.browser;

public interface BrowserConfig {
    static BrowserConfig getInstance(){
        return LocalBrowserConfig.BROWSER_CONFIG;
    }
    String pageLoadStrategy();
    String browser();

}
