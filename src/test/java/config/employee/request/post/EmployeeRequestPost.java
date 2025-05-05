package config.employee.request.post;

public class EmployeeRequestPost {
    static EmployeePostConfig config;

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
