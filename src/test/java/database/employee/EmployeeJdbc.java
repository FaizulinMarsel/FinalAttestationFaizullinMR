package database.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeJdbc {
    private final Connection connection;

    public EmployeeJdbc(Connection connection) {
        this.connection = connection;
    }

    public void deleteEmployeesByCompanyId(int companyId) throws SQLException {
        String DELETE_EMPLOYEES = """
                DELETE FROM employee WHERE company_id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEES)) {
            preparedStatement.setInt(1, companyId);
            preparedStatement.executeUpdate();
        }
    }
}
