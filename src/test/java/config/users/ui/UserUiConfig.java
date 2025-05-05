package config.users.ui;

public interface UserUiConfig {
    static UserUiConfig getInstance(){
        return LocalUserUiConfig.USER_UI_CONFIG;
    }
    String standardUser();
    String lockedUser();
    String glitchedUser();
    String password();

}
