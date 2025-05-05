package tests.api.xclients.business;

import config.employee.request.TestData;
import config.employee.request.path.CreateEmployeeRequestPath;
import config.employee.request.path.EmployeePathConfig;
import config.employee.request.path.EmployeeRequestPath;
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
import static org.hamcrest.Matchers.equalTo;

public class BusinessPathTest {
    String token;
    int companyId;
    String employeeEmail;
    int employeeId;
    boolean isActive;
    String phone;
    String url;
    String email;
    String lastName;
    Response response;
    static TestData testData;
    static AuthRequest authRequest;
    static ConnectionDataBase connectionDataBase;
    static UserAdmin userAdmin;
    static Auth auth;
    static CompanyServiceJdbc companyServiceJdbc;
    static CreateEmployeeRequestPost createEmployeeRequestPost;
    static CreateEmployeeRequestPath createEmployeeRequestPath;
    static EmployeePathConfig employeePathConfig;
    static EmployeeRequestPath employeeRequestPath;
    static EmployeeJdbc employeeJdbc;


    @BeforeAll
    public static void setUp() throws SQLException {
        connectionDataBase = new ConnectionDataBase();
        companyServiceJdbc = new CompanyServiceJdbc(connectionDataBase.getConnection());
        userAdmin = new UserAdmin(connectionDataBase.getConnection());
        auth = new Auth();
        employeeJdbc = new EmployeeJdbc(connectionDataBase.getConnection());
        testData = new TestData();
        employeePathConfig = EmployeePathConfig.getInstance();
        employeeRequestPath = new EmployeeRequestPath();
    }

    @BeforeEach
    public void prepareRequest() throws SQLException {
        authRequest = userAdmin.createUserAdmin();
        token = auth.authAndGetToken(authRequest);
        companyId = companyServiceJdbc.createCompany();
        employeeEmail = EmployeePostConfig.getInstance().getEmailEmployee();
        createEmployeeRequestPost = EmployeeRequestPost.getEmployeeRequest(companyId, employeeEmail);
        response = sendPostRequest(createEmployeeRequestPost, token);
        employeeId = response.jsonPath().get("id");

        lastName = employeePathConfig.getLastName();
        email = employeePathConfig.getEmail();
        url = employeePathConfig.getUrl();
        phone = employeePathConfig.getPhone();
        isActive = employeePathConfig.isActive();

        createEmployeeRequestPath = employeeRequestPath.createRequestPath(lastName, email, url, phone, isActive);
    }
    @AfterEach
    public void tearDown() throws SQLException {
        employeeJdbc.deleteEmployeesByCompanyId(companyId);
        companyServiceJdbc.deleteCompany(companyId);
        userAdmin.deleteUserAdmin(authRequest.login());
    }
    @Test
    @DisplayName("Валидация измененных полей")
    public void checkChangeEmployee() {
        sendPatchRequest(employeeId, createEmployeeRequestPath, token)
                .then()
                .body("id", equalTo(employeeId))
                .body("isActive", equalTo(isActive))
                .body("email", equalTo(email))
                .body("url", equalTo(url));
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

    private Response sendPatchRequest(int employeeId, Object body, String token) {
        return given()
                .basePath("employee/{id}")
                .pathParam("id", employeeId)
                .header("x-client-token", token)
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .patch();
    }
}
