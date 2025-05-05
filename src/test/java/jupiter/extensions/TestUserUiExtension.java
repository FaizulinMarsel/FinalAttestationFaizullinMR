package jupiter.extensions;

import config.users.ui.UserUiConfig;
import jupiter.annotations.WithTestUserUi;
import model.TestUserUi;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

public class TestUserUiExtension implements ParameterResolver {
    private static final UserUiConfig CFG = UserUiConfig.getInstance();
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                WithTestUserUi.class
        ).isPresent() && parameterContext.getParameter().getType() == TestUserUi.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        WithTestUserUi annotation = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                WithTestUserUi.class
        ).orElseThrow(() -> new IllegalArgumentException("Can not find WithTestUser annotation above test method!"));
        return switch (annotation.type()){
            case Standard -> new TestUserUi(CFG.standardUser(), CFG.password());
            case LockedOut -> new TestUserUi(CFG.lockedUser(), CFG.password());
        };
    }
}
