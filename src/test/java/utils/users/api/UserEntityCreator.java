package utils.users.api;

import database.create.users.UserEntity;
import io.qameta.allure.Step;

public class UserEntityCreator {
/*    public static UserEntity userAdmin(){
        return createUser(Role.ADMIN);
    }

    public static UserEntity userClient(){
        return createUser(Role.CLIENT);
    }*/
    @Step("Создание пользователя с ролью {role}")
    public static UserEntity createUser(Role role){
        UserApiUtils config = getUserApiConfigByRole(role);

        return new UserEntity(
                config.IsActive(),
                config.login(),
                config.password(),
                config.displayName(),
                config.role().name().toLowerCase()
        );
    }

    private static UserApiUtils getUserApiConfigByRole(Role role) {
        switch (role) {
            case ADMIN:
                return LocalUserApiUtils.USER_API_CONFIG_ADMIN;
            case CLIENT:
                return LocalUserApiUtils.USER_API_CONFIG_CLIENT;
            default:
                throw new IllegalArgumentException("Роль не найдена: " + role);
        }
    }
}
