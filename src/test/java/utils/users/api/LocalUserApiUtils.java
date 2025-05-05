package utils.users.api;

import utils.FakeTestData;

import java.util.Random;

public enum LocalUserApiUtils implements UserApiUtils {

    USER_API_CONFIG_ADMIN(Role.ADMIN),
    USER_API_CONFIG_CLIENT(Role.CLIENT);
    private final Role role;

    LocalUserApiUtils(Role role) {
        this.role = role;
    }

    @Override
    public boolean IsActive() {
        return new Random().nextBoolean();
    }

    @Override
    public String login() {
        return FakeTestData.login;
    }

    @Override
    public String password() {
        return FakeTestData.password;
    }

    @Override
    public String displayName() {
        return FakeTestData.login;
    }

    @Override
    public Role role() {
        return this.role;
    }
}
