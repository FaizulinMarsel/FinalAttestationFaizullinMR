package tests.api.xclients.business;

import config.employee.request.TestData;
import config.employee.request.post.CreateEmployeeRequestPost;
import config.employee.request.post.EmployeePostConfig;
import config.employee.request.post.EmployeeRequestPost;
import database.company.CompanyServiceJdbc;
import database.connection.ConnectionDataBase;
import database.employee.EmployeeJdbc;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import utils.UserAdmin;
import utils.auth.Auth;
import utils.auth.AuthRequest;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusinessPostTest {
    String token;
    int companyId;
    String employeeEmail;
    String nonexistentUserToken;
    String incorrectEmployeeEmail;
    int nonexistentId;
    Response response;
    static TestData testData;
    static AuthRequest authRequest;
    static ConnectionDataBase connectionDataBase;
    static UserAdmin userAdmin;
    static Auth auth;
    static CompanyServiceJdbc companyServiceJdbc;
    static CreateEmployeeRequestPost createEmployeeRequestPost;
    static EmployeeJdbc employeeJdbc;


    @BeforeAll
    public static void setUp() throws SQLException {
        connectionDataBase = new ConnectionDataBase();
        companyServiceJdbc = new CompanyServiceJdbc(connectionDataBase.getConnection());
        userAdmin = new UserAdmin(connectionDataBase.getConnection());
        auth = new Auth();
        employeeJdbc = new EmployeeJdbc(connectionDataBase.getConnection());
        testData = new TestData();
    }

    @BeforeEach
    public void prepareRequest() throws SQLException {
        authRequest = userAdmin.createUserAdmin();
        token = auth.authAndGetToken(authRequest);
        companyId = companyServiceJdbc.createCompany();
        employeeEmail = EmployeePostConfig.getInstance().getEmailEmployee();
        createEmployeeRequestPost = EmployeeRequestPost.getEmployeeRequest(companyId, employeeEmail);
        response = sendPostRequest(createEmployeeRequestPost, token);
    }
    @AfterEach
    public void tearDown() throws SQLException {
        employeeJdbc.deleteEmployeesByCompanyId(companyId);
        companyServiceJdbc.deleteCompany(companyId);
        userAdmin.deleteUserAdmin(authRequest.login());
    }
    @Test
    @DisplayName("Проверяем, что возвращенный id > 0")
    public void addEmployeeCheckId() {
        assertTrue(response.jsonPath().getInt("id") > 0);
    }
    private static Response sendPostRequest(Object bodyRequest, String token) {
        return given()
                .basePath("employee")
                .header("x-client-token", token)
                .body(bodyRequest)
                .contentType(ContentType.JSON)
                .when()
                .post();
    }
}
