package tests.api.xclients.contracts;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContractGetTest {
    @Test
    @DisplayName("Статус 200. Получение сотрудника по id")
    public void checkGetEmployeeId() {}

    @Test
    @DisplayName("Статус 200. Несуществующий id")
    public void checkNonexistentEmployeeId() {}

    @Test
    @DisplayName("Запрос по сотруднику. Проверяем, что в ответе приходит JSON-файл")
    public void checkGetJsonEmployeeId() {}

    @Test
    @DisplayName("Статус 200. Получить список сотрудников для компании")
    public void checkGetListEmployeeCompanyIdStatusOk() {}

    @Test
    @DisplayName("Статус 200. Несуществующий id")
    public void checkNonexistentCompanyId() {}

    @Disabled("Проблема с contentType. Тест работает не стабильно,иногда отрабатывает, иногда ошибку выдает")
    @Test
    @DisplayName("Запрос по компании. Проверяем, что в ответе приходит JSON-файл")
    public void checkGetJsonCompanyId() {}
}
