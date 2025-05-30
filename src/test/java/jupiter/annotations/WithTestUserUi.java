package jupiter.annotations;

import jupiter.extensions.TestUserUiExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith({TestUserUiExtension.class})
public @interface WithTestUserUi {
    Type type();

    enum Type {
        Standard, LockedOut
    }
}
