package utils.auth;

import config.urls.UrlsConfig;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class Auth {
    UrlsConfig URl = UrlsConfig.getInstance();
    @Step("Авторизация и получение токена")
    public String authAndGetToken(AuthRequest authRequest) {
        RestAssured.baseURI = URl.UrlApiXclient();
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
