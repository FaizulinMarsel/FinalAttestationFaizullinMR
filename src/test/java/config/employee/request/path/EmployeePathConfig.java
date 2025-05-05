package config.employee.request.path;

public interface EmployeePathConfig {
    static EmployeePathConfig getInstance() {
        return LocalEmployeePathConfig.LOCAL_EMPLOYEE_PATH;
    }

    String getLastName();

    String getEmail();

    String getUrl();

    String getPhone();

    boolean isActive();
}
