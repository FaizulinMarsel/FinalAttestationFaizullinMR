package utils.users.api;

public interface UserApiUtils {

static UserApiUtils getInstance(Role role) {
    switch (role) {
        case ADMIN:
            return LocalUserApiUtils.USER_API_CONFIG_ADMIN;
        case CLIENT:
            return LocalUserApiUtils.USER_API_CONFIG_CLIENT;
        default:
            throw new IllegalArgumentException("Роль не найдена: " + role);
    }
}
    boolean IsActive();

    String login();

    String password();

    String displayName();

    Role role();

}
