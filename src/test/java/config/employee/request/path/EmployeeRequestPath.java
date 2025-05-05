package config.employee.request.path;

import io.qameta.allure.Step;

public class EmployeeRequestPath {
    static EmployeePathConfig config;
    @Step("Создание запроса на изменение данных сотрудника")
    public CreateEmployeeRequestPath createRequestPath(String lastName, String email, String url, String phone, boolean isActive) {
        config = EmployeePathConfig.getInstance();
        return new CreateEmployeeRequestPath(lastName, email, url, phone, isActive);
    }
}
