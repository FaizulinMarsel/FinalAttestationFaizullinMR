package config.db;

public interface DbConfig {
    static DbConfig getInstance() {
        return LocalDbConfig.DB_CONFIG;
    }

    String dbHost();

    String dbUserName();

    String dbUserPassword();
}
