package database.create.users;

import utils.auth.AuthRequest;
import io.qameta.allure.Step;

import java.sql.*;

public class UserServiceJdbc implements UserService{
    Connection connection;
    public UserServiceJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    @Step("Создание компании")
    public AuthRequest createNew(UserEntity entity) throws SQLException {
        String INSERT_USER = """
                insert into app_users (is_active, login, "password", display_name, "role")
                values (?, ?, ?, ?, ?::app_users_role_enum)
                 """;
        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_USER,
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setBoolean(1, entity.isActive());
        preparedStatement.setString(2, entity.getLogin());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setString(4, entity.getDisplayName());
        preparedStatement.setObject(5, entity.getRole());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        return new AuthRequest(resultSet.getString("login"), resultSet.getString("password"));
    }

    @Override
    @Step("Удаление компании")
    public void deleteUserByLogin(String login) throws SQLException {
        String DELETE_USER = """
                DELETE FROM app_users WHERE login = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
        preparedStatement.setString(1, login);
        preparedStatement.executeUpdate();
    }
}
