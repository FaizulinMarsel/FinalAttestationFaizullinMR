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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContractPostTest {
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
    @DisplayName("Статус 201. Сотрудник добавлен")
    public void addEmployeeStatusOk() {
        response.then().statusCode(201);
    }
    @Test
    @DisplayName("Статус 401. Передача неверного токена")
    public void nonexistentUserToken() {
        nonexistentUserToken = testData.getIncorrectUserToken();
        response = sendPostRequest(createEmployeeRequestPost, nonexistentUserToken);
        response.then().statusCode(401);
    }
    @Test
    @DisplayName("Статус 400. Неверный e-mail")
    public void nonexistentEmail() {
        incorrectEmployeeEmail = testData.getIncorrectEmail();
        createEmployeeRequestPost = EmployeeRequestPost.getEmployeeRequest(companyId, incorrectEmployeeEmail);
        Response response = sendPostRequest(createEmployeeRequestPost, token);
        response.then().statusCode(400);
    }

    @Test
    @DisplayName("Статус 400. Сломанный JSON")
    public void brokenJson() {
        incorrectEmployeeEmail = testData.getBrokenJson();
        createEmployeeRequestPost = EmployeeRequestPost.getEmployeeRequest(companyId, incorrectEmployeeEmail);
        Response response = sendPostRequest(createEmployeeRequestPost, token);
        response.then().statusCode(400);
    }

    @Test
    @DisplayName("Статус 500. Передача несуществующего id компании")
    public void nonexistentCompanyId() {
        nonexistentId = testData.getIncorrectCompanyId();
        createEmployeeRequestPost = EmployeeRequestPost.getEmployeeRequest(nonexistentId, employeeEmail);
        Response response = sendPostRequest(createEmployeeRequestPost, token);
        response.then().statusCode(500);
    }

    @Test
    @DisplayName("Проверяем, что в ответе приходит JSON-файл")
    public void correctJson() {
        response.then().contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Проверяем формат JSON-файла")
    public void checkFormatJson() {
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.startsWith("{"));
        assertTrue(responseBody.endsWith("}"));
    }

    @Test
    @DisplayName("Проверяем поля JSON-файла")
    public void checkFiledJsonFile() {
        assertNotNull(response.jsonPath().get("id"));
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
