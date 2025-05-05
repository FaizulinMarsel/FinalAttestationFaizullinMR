package jupiter.arguments.ui;

import config.users.ui.UserUiConfig;
import model.TestUserUi;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class TestUsersUiDataProvider implements ArgumentsProvider {
    private static final UserUiConfig CFG = UserUiConfig.getInstance();
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(
                        Named.of(
                                "Авторизоваться как " + CFG.standardUser(),
                                new TestUserUi(CFG.standardUser(), CFG.password())
                        )
                ),
                Arguments.of(
                        Named.of(
                                "Авторизоваться как " + CFG.glitchedUser(),
                                new TestUserUi(CFG.glitchedUser(), CFG.password())
                        )
                )
        );
    }
    }

