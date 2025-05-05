package tests.api.xclients.contracts;

import config.employee.request.TestData;
import config.employee.request.post.CreateEmployeeRequestPost;
import config.employee.request.post.EmployeePostConfig;
import config.employee.request.post.EmployeeRequestPost;
import config.urls.UrlsConfig;
import database.employee.EmployeeJdbc;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jupiter.annotations.api.WithTestCompanyApi;
import jupiter.annotations.api.WithTestUserApi;
import jupiter.extensions.api.CompanyCreateExtension;
import jupiter.extensions.api.DatabaseConnectionExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import utils.auth.Auth;
import utils.auth.AuthRequest;

import java.sql.Connection;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;

@ExtendWith({DatabaseConnectionExtension.class, CompanyCreateExtension.class})
public class ContractPostTest {
    Response response;
    String nonexistentUserToken;

    static TestData testData;
    static CreateEmployeeRequestPost bodyRequest;
    static EmployeeJdbc employeeJdbc;
    private static Auth auth;
    private static UrlsConfig URL;
    @BeforeAll
    public static void setUp(){
        URL = UrlsConfig.getInstance();
        auth = new Auth();
        employeeJdbc = new EmployeeJdbc(DatabaseConnectionExtension.getConnection());
        testData = new TestData();

    }
    @AfterEach
    public void tearDown() throws SQLException {
        employeeJdbc.deleteEmployeesByCompanyId(bodyRequest.companyId());
    }
    @Test
    @DisplayName("Статус 201. Сотрудник добавлен")
    @WithTestUserApi(type = WithTestUserApi.Type.ADMIN)
    @WithTestCompanyApi
    public void addEmployeeStatusOk(AuthRequest authRequest, Integer companyId) {
        String token = auth.authAndGetToken(URL.UrlApiXclient(), authRequest);
        bodyRequest = EmployeeRequestPost.getEmployeeRequest(companyId, EmployeePostConfig.getInstance().getEmailEmployee());
        response = sendPostRequest(bodyRequest, token);
        response.then().statusCode(201);
    }

    @Test
    @DisplayName("Статус 401. Передача неверного токена")
    @WithTestUserApi(type = WithTestUserApi.Type.ADMIN)
    @WithTestCompanyApi
    public void nonexistentUserToken(AuthRequest authRequest,Integer companyId) {
        auth.authAndGetToken(URL.UrlApiXclient(), authRequest);
        nonexistentUserToken = testData.getIncorrectUserToken();
        bodyRequest = EmployeeRequestPost.getEmployeeRequest(companyId, EmployeePostConfig.getInstance().getEmailEmployee());
        response = sendPostRequest(bodyRequest, nonexistentUserToken);
        response.then().statusCode(401);
    }

    @Test
    @DisplayName("Статус 400. Неверный e-mail")
    public void nonexistentEmail() {}

    @Test
    @DisplayName("Статус 400. Сломанный JSON")
    public void brokenJson() {}

    @Test
    @DisplayName("Статус 500. Передача несуществующего id компании")
    public void nonexistentCompanyId() {}

    @Test
    @DisplayName("Проверяем, что в ответе приходит JSON-файл")
    public void correctJson() {}

    @Test
    @DisplayName("Проверяем формат JSON-файла")
    public void checkFormatJson() {}

    @Test
    @DisplayName("Проверяем поля JSON-файла")
    public void checkFiledJsonFile() {}

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
