package config.employee.request.post;

public interface EmployeePostConfig {
    static EmployeePostConfig getInstance() {
        return LocalEmployeePostConfig.LOCAL_EMPLOYEE_CONFIG;
    }

    int getIdEmployee();

    String getFirstNameEmployee();

    String getLastNameEmployee();

    String getMiddleNameEmployee();

    String getEmailEmployee();

    String getUrlEmployee();

    String getPhoneEmployee();

    String getBirthdateEmployee();

    boolean isActiveEmployee();
}
