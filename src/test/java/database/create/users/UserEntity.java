package database.create.users;

public class UserEntity {

    private final String login;
    private final String password;
    private final String displayName;
    private final String role;
    private final boolean isActive;
    public UserEntity(boolean isActive, String login, String password, String displayName, String role) {
        this.login = login;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
        this.isActive = isActive;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRole() {
        return role;
    }

    public boolean isActive() {
        return isActive;
    }
}
