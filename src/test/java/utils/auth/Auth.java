package utils.auth;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class Auth {
    @Step("Авторизация и получение токена")
    public String authAndGetToken(String URL, AuthRequest authRequest) {
        RestAssured.baseURI = URL;
        AuthResponse authResponse = given()
                .basePath("auth/login")
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .post()
                .as(AuthResponse.class);
        return authResponse.userToken();
    }
}
