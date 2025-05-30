package tests.api.xclients.contracts;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContractPathTest {
    String token;
    int companyId;
    String employeeEmail;
    int employeeId;
    boolean isActive;
    String phone;
    String url;
    String email;
    String lastName;

    String nonexistentUserToken;
    int incorrectEmployeeId;
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
    @DisplayName("Статус 200. Изменение данных сотрудника")
    public void checkChangeEmployeeStatusOk() {
        sendPatchRequest(employeeId, createEmployeeRequestPath, token)
                .then()
                .statusCode(200);
    }
    @Test
    @DisplayName("Проверяем, что в ответе приходит JSON-файл")
    public void checkCorrectJson() {
        String responseBody = sendPatchRequest(employeeId, createEmployeeRequestPath, token)
                .then()
                .contentType(ContentType.JSON)
                .extract().asString();
        assertTrue(responseBody.startsWith("{"));
        assertTrue(responseBody.endsWith("}"));
    }

    @Test
    @DisplayName("Статус 500. Передан несуществующий id Employee")
    public void checkErrorStatusNonexistentIdEmployee() {
        incorrectEmployeeId = testData.getIncorrectEmployeeId();
        sendPatchRequest(incorrectEmployeeId, createEmployeeRequestPath, token)
                .then()
                .statusCode(500)
                .body("message", equalTo("Internal server error"));
    }

    @Test
    @DisplayName("Статус 401. Неверный токен")
    public void checkNonexistentUserToken() {
        nonexistentUserToken = testData.getIncorrectUserToken();
        sendPatchRequest(employeeId, createEmployeeRequestPath, nonexistentUserToken)
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Статус 400. Передан пустой lastName")
    public void checkNullLastName() {
        lastName = testData.getIncorrectLastName();
        createEmployeeRequestPath = employeeRequestPath.createRequestPath(lastName, email, url, phone, isActive);
        sendPatchRequest(employeeId, createEmployeeRequestPath, token)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Статус 400. Передан некорректный email")
    public void checkNonexistentEmailErrorStatus() {
        email = testData.getIncorrectEmail();
        createEmployeeRequestPath = employeeRequestPath.createRequestPath(lastName, email, url, phone, isActive);
        sendPatchRequest(employeeId, createEmployeeRequestPath, token)
                .then()
                .statusCode(400);
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
