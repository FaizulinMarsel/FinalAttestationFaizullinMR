package config.employee.request.path;

import static config.employee.request.TestData.PROPS;

public enum LocalEmployeePathConfig implements EmployeePathConfig {
    LOCAL_EMPLOYEE_PATH;
    @Override
    public String getLastName() {
        return PROPS.getProperty("employee.change.lastName");
    }

    @Override
    public String getEmail() {
        return PROPS.getProperty("employee.change.email");
    }

    @Override
    public String getUrl() {
        return PROPS.getProperty("employee.change.url");
    }

    @Override
    public String getPhone() {
        return PROPS.getProperty("employee.change.phone");
    }

    @Override
    public boolean isActive() {
        return Boolean.parseBoolean(PROPS.getProperty("employee.change.isActive"));
    }
}
