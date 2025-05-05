package database.company;

import java.sql.SQLException;

public interface CompanyService {

    int createCompany(CompanyEntity entity) throws SQLException;
    void deleteCompany(int companyId) throws SQLException;

}
