package config.employee.request.post;

import io.qameta.allure.Step;

public class EmployeeRequestPost {
    static EmployeePostConfig config;
    @Step("Отправка запроса на создание сотрудника")
    public static CreateEmployeeRequestPost getEmployeeRequest(int companyId, String email) {
        config = EmployeePostConfig.getInstance();
        return new CreateEmployeeRequestPost(
                config.getIdEmployee(),
                config.getFirstNameEmployee(),
                config.getLastNameEmployee(),
                config.getMiddleNameEmployee(),
                companyId,
                email,
                config.getUrlEmployee(),
                config.getPhoneEmployee(),
                config.getBirthdateEmployee(),
                config.isActiveEmployee()
        );
    }
}
