package config.users.ui;

import config.ProjectConfig;

import java.util.Properties;

public enum LocalUserUiConfig implements UserUiConfig{
    USER_UI_CONFIG;

    private static final Properties PROPS = ProjectConfig.readProperties(ProjectConfig.Env.USERS_UI);

    @Override
    public String standardUser() {
        return PROPS.getProperty("standardUser");
    }

    @Override
    public String lockedUser() {
        return PROPS.getProperty("lockedUser");
    }

    @Override
    public String glitchedUser() {
        return PROPS.getProperty("glitchedUser");
    }

    @Override
    public String password() {
        return PROPS.getProperty("password");
    }
}
