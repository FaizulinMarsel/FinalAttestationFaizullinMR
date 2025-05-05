package jupiter.annotations.api;

import jupiter.extensions.api.CompanyCreateExtension;
import jupiter.extensions.api.TestUserApiExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith({CompanyCreateExtension.class})
public @interface WithTestCompanyApi {
}
