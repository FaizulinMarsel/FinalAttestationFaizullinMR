package database.connection;

import config.db.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {
    public Connection getConnection() throws SQLException{
        String connectionString = DbConfig.getInstance().dbHost();
        String login = DbConfig.getInstance().dbUserName();
        String password = DbConfig.getInstance().dbUserPassword();
        return DriverManager.getConnection(connectionString, login, password);
    }
}
