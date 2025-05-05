package database.create.users;

import utils.auth.AuthRequest;

import java.sql.SQLException;

public interface UserService {
    AuthRequest createNew(UserEntity entity) throws SQLException;
    void deleteUserByLogin(String login) throws SQLException;
}
