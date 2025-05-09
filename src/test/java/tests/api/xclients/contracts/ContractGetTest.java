package tests.api.xclients.contracts;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContractGetTest {
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
    @DisplayName("Статус 200. Получение сотрудника по id")
    public void checkGetEmployeeId() {
        sendGetRequestEmployeeId(employeeId)
                .then()
                .statusCode(200);
    }
    @Test
    @DisplayName("Статус 200. Несуществующий id")
    public void checkNonexistentEmployeeId() {
        incorrectEmployeeId = testData.getIncorrectEmployeeId();
        sendGetRequestEmployeeId(incorrectEmployeeId)
                .then()
                .statusCode(200)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("Запрос по сотруднику. Проверяем, что в ответе приходит JSON-файл")
    public void checkGetJsonEmployeeId() {
        String responseBody = sendGetRequestEmployeeId(employeeId)
                .then()
                .contentType(ContentType.JSON)
                .extract().asString();
        assertTrue(responseBody.startsWith("{"));
        assertTrue(responseBody.endsWith("}"));
    }

    @Test
    @DisplayName("Статус 200. Получить список сотрудников для компании")
    public void checkGetListEmployeeCompanyIdStatusOk() {
        System.out.println(companyId);
        sendGetRequestCompanyId(companyId)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Статус 200. Несуществующий id")
    public void checkNonexistentCompanyId() {
        incorrectCompanyId = testData.getIncorrectCompanyId();
        sendGetRequestCompanyId(incorrectCompanyId)
                .then()
                .statusCode(200)
                .body(equalTo(""));
    }

    @Disabled("Проблема с contentType. Тест работает не стабильно,иногда отрабатывает, иногда ошибку выдает")
    @Test
    @DisplayName("Запрос по компании. Проверяем, что в ответе приходит JSON-файл")
    public void checkGetJsonCompanyId() {
        String responseBody =
                sendGetRequestCompanyId(companyId)
                        .then()
                        .contentType(ContentType.JSON)
                        .extract().asString();
        assertTrue(responseBody.startsWith("{"));
        assertTrue(responseBody.endsWith("}"));
        System.out.println(responseBody);
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
