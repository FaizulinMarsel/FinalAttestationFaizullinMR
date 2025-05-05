package jupiter.extensions.api;

import database.create.users.UserEntity;
import database.create.users.UserServiceJdbc;
import utils.auth.AuthRequest;
import jupiter.annotations.api.WithTestUserApi;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import utils.users.api.Role;
import utils.users.api.UserEntityCreator;

import java.sql.Connection;
import java.sql.SQLException;

public class TestUserApiExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
    private static Connection connection;
    private static UserServiceJdbc userServiceJdbc;
    private static AuthRequest authRequest;
    private static UserEntity userEntity;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        connection = DatabaseConnectionExtension.getConnection();
        userServiceJdbc = new UserServiceJdbc(connection);
    }
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        userServiceJdbc.deleteUserByLogin(authRequest.username());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                WithTestUserApi.class
        ).isPresent() && parameterContext.getParameter().getType() == AuthRequest.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        WithTestUserApi annotation = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                WithTestUserApi.class
        ).orElseThrow(() -> new IllegalArgumentException("Can not find WithTestUser annotation above test method!"));
        switch (annotation.type()){
            case ADMIN:
                userEntity = UserEntityCreator.createUser(Role.ADMIN);
                try {
                    authRequest = userServiceJdbc.createNew(userEntity);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка создания пользователя ADMIN");
                }
                break;
            case CLIENT:
                userEntity = UserEntityCreator.createUser(Role.CLIENT);
                try {
                    authRequest = userServiceJdbc.createNew(userEntity);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка создания пользователя CLIENT");
                }
                break;
            }
        return authRequest;
    }
}
