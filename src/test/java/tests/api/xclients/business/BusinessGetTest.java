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
import static org.hamcrest.Matchers.equalTo;

public class BusinessGetTest {
    String token;
    int companyId;
    String employeeEmail;
    int employeeId;
    int incorrectCompanyId;
    int incorrectEmployeeId;
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
        employeeId = response.jsonPath().getInt("id");
    }
    @AfterEach
    public void tearDown() throws SQLException {
        employeeJdbc.deleteEmployeesByCompanyId(companyId);
        companyServiceJdbc.deleteCompany(companyId);
        userAdmin.deleteUserAdmin(authRequest.login());
    }
    @Test
    @DisplayName("Валидация ответа запроса на получение сотрудника по id")
    public void checkValidateResponseGetEmployeeId() {
        sendGetRequestEmployeeId(employeeId)
                .then()
                .body("id", equalTo(employeeId))
                .body("isActive", equalTo(createEmployeeRequestPost.isActive()))
                .body("firstName", equalTo(createEmployeeRequestPost.firstName()))
                .body("lastName", equalTo(createEmployeeRequestPost.lastName()))
                .body("middleName", equalTo(createEmployeeRequestPost.middleName()))
                .body("phone", equalTo(createEmployeeRequestPost.phone()))
                .body("email", equalTo(null))
                .body("birthdate", equalTo(createEmployeeRequestPost.birthdate()))
                .body("avatar_url", equalTo(createEmployeeRequestPost.url()))
                .body("companyId", equalTo(companyId));
    }

    @Disabled("Проблема с contentType. Тест работает не стабильно,иногда отрабатывает, иногда ошибку выдает")
    @Test
    @DisplayName("Валидация ответа запроса на получение cписка сотрудников по id компании")
    public void checkValidateResponseGetCompanyId() {
        sendGetRequestCompanyId(companyId)
                .then()
                .contentType(ContentType.JSON)
                .body("id", equalTo(employeeId))
                .body("isActive", equalTo(createEmployeeRequestPost.isActive()))
                .body("firstName", equalTo(createEmployeeRequestPost.firstName()))
                .body("lastName", equalTo(createEmployeeRequestPost.lastName()))
                .body("middleName", equalTo(createEmployeeRequestPost.middleName()))
                .body("phone", equalTo(createEmployeeRequestPost.phone()))
                .body("email", equalTo(null))
                .body("birthdate", equalTo(createEmployeeRequestPost.birthdate()))
                .body("avatar_url", equalTo(createEmployeeRequestPost.url()))
                .body("companyId", equalTo(companyId));
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

    private Response sendGetRequestCompanyId(int companyId) {
        return given()
                .basePath("employee/{id}")
                .pathParam("id", companyId)
                .when()
                .get();
    }

    private Response sendGetRequestEmployeeId(int employeeId) {
        return given()
                .basePath("employee/{id}")
                .pathParam("id", employeeId)
                .when()
                .get();
    }
}
