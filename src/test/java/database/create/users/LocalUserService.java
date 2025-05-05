package database.create.users;

import utils.FakeTestData;

import java.util.Random;

public enum LocalUserService implements UserService{
    LOCAL_USER_SERVICE;
    private Role role;
    //private static final Properties propsValue = PropertiesUtil.readProperties();

    @Override
    public boolean getIsActive() {
        return new Random().nextBoolean();
    }

    @Override
    public String getLogin() {
        return FakeTestData.login;
    }

    @Override
    public String getPassword() {
        return FakeTestData.password;
    }

    @Override
    public String getDisplayName() {
        return FakeTestData.login;
    }

    @Override
    public String getRole() {
        return role.name();
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

}
