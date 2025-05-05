package utils;

import database.create.users.Role;
import database.create.users.UserService;
import database.create.users.UserServiceJdbc;
import io.qameta.allure.Step;
import utils.auth.AuthRequest;

import java.sql.Connection;
import java.sql.SQLException;

public class UserAdmin extends UserServiceJdbc {
    AuthRequest authRequest;
    UserService userService;

    public UserAdmin(Connection connection) {
        super(connection);
        this.userService = UserService.getInstance();
    }
    @Step("Создание пользователя Admin")
    public AuthRequest createUserAdmin() throws SQLException {
        userService.setRole(Role.ADMIN);
        authRequest = createUser(
                userService.getIsActive(),
                userService.getLogin(),
                userService.getPassword(),
                userService.getDisplayName(),
                userService.getRole().toLowerCase()
        );
        return new AuthRequest(authRequest.login(), authRequest.password());
    }
    @Step("Удаление пользователя Admin")
    public void deleteUserAdmin(String adminLogin) throws SQLException {
        deleteUser(adminLogin);
    }
}
