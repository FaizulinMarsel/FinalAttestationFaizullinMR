package jupiter.extensions.api;

import database.connection.ConnectionDataBase;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.sql.Connection;

public class DatabaseConnectionExtension implements BeforeAllCallback, AfterAllCallback {
    private static Connection connection;
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("DB");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        connection = new ConnectionDataBase().getConnection();
        context.getStore(NAMESPACE).put("connection", connection);
    }
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Connection connection = context.getStore(NAMESPACE).remove("connection", Connection.class);
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public static Connection getConnection() {
        return connection;
    }
   /* public static Connection getConnection(ExtensionContext context) {
        return context.getStore(NAMESPACE).get("connection", Connection.class);
    }*/
}
