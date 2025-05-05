package config.employee.request.path;

public class EmployeeRequestPath {
    static EmployeePathConfig config;

    public CreateEmployeeRequestPath createRequestPath(String lastName, String email, String url, String phone, boolean isActive) {
        config = EmployeePathConfig.getInstance();
        return new CreateEmployeeRequestPath(lastName, email, url, phone, isActive);
    }
}
