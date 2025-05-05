
package database.company;

import java.sql.*;

public class CompanyServiceJdbc implements CompanyService{
    Connection connection;
    public CompanyServiceJdbc(Connection connection){
        this.connection = connection;
    }

    public int createCompany(CompanyEntity entity) throws SQLException {

        String INSERT_COMPANY = """
                insert into company (is_active, "name", description)
                values (?, ?, ?)
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT_COMPANY, Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setBoolean(1, entity.isActiveCompany());
        preparedStatement.setString(2, entity.getNameCompany());
        preparedStatement.setString(3, entity.getDescriptionCompany());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt("id");
    }

    public void deleteCompany(int companyId) throws SQLException {
        String DELETE_COMPANY = """
                delete from company c
                where c.id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY);
        preparedStatement.setInt(1, companyId);
        preparedStatement.executeUpdate();
    }
}
