package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectConfig {
    public static Properties readProperties(Env env) {
        final String path = String.format("src/test/resources/%s", env);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public enum Env {
        DB("database.properties"),
        BROWSER("browser.properties"),
        URLS("urls.properties"),
        ENV("env.properties"),
        USERS_UI("users.ui.properties"),
        TEST_DATA("testdata.properties");

        private final String path;

        Env(String path) {
            this.path = path;
        }

        @Override
        public String toString() { return path; }
    }
}
