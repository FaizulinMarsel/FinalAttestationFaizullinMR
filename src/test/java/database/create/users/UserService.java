package database.create.users;

public interface UserService {
    static UserService getInstance() {
        return LocalUserService.LOCAL_USER_SERVICE;
    }

    boolean getIsActive();

    String getLogin();

    String getPassword();

    String getDisplayName();

    String getRole();

    void setRole(Role role);
}
