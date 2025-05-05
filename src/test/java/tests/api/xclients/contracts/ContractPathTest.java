package tests.api.xclients.contracts;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContractPathTest {
    @Test
    @DisplayName("Статус 200. Изменение данных сотрудника")
    public void checkChangeEmployeeStatusOk() {}

    @Test
    @DisplayName("Проверяем, что в ответе приходит JSON-файл")
    public void checkCorrectJson() {}

    @Test
    @DisplayName("Статус 500. Передан несуществующий id Employee")
    public void checkErrorStatusNonexistentIdEmployee() {}

    @Test
    @DisplayName("Статус 401. Неверный токен")
    public void checkNonexistentUserToken() {}

    @Test
    @DisplayName("Статус 400. Передан пустой lastName")
    public void checkNullLastName() {}

    @Test
    @DisplayName("Статус 400. Передан некорректный email")
    public void checkNonexistentEmailErrorStatus() {}
}
