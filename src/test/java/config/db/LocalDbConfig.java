package config.db;

import config.ProjectConfig;

import java.util.Properties;

public enum LocalDbConfig implements DbConfig {
    DB_CONFIG;

    private static final Properties PROPS = ProjectConfig.readProperties(ProjectConfig.Env.DB);

    @Override
    public String dbHost() {
        return PROPS.getProperty("connectionHost");
    }

    @Override
    public String dbUserName() {
        return PROPS.getProperty("login");
    }

    @Override
    public String dbUserPassword() {
        return PROPS.getProperty("password");
    }
}
