package jupiter.extensions.api;

import database.company.CompanyEntity;
import database.company.CompanyServiceJdbc;
import database.create.users.UserServiceJdbc;
import jupiter.annotations.api.WithTestCompanyApi;
import jupiter.annotations.api.WithTestUserApi;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import utils.CompanyEntityCreator;

import java.sql.Connection;
import java.sql.SQLException;

public class CompanyCreateExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
    int companyId;
    Connection connection;
    CompanyServiceJdbc companyServiceJdbc;
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        companyServiceJdbc.deleteCompany(companyId);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        connection = DatabaseConnectionExtension.getConnection();
        companyServiceJdbc = new CompanyServiceJdbc(connection);

    }
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                WithTestCompanyApi.class
        ).isPresent() && parameterContext.getParameter().getType() == Integer.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        //WithTestCompanyApi annotation =
                AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                WithTestCompanyApi.class
        ).orElseThrow(() -> new IllegalArgumentException("Can not find WithTestUser annotation above test method!"));
        CompanyEntity companyEntity = CompanyEntityCreator.createCompany();
        try {
            companyId = companyServiceJdbc.createCompany(companyEntity);
            return companyId;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания компании");
        }
    }

}
