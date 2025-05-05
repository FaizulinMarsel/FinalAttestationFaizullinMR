package config.employee.request.post;

import static config.employee.request.TestData.PROPS;

public enum LocalEmployeePostConfig implements EmployeePostConfig {
    LOCAL_EMPLOYEE_CONFIG;

    @Override
    public int getIdEmployee() {
        return Integer.parseInt(PROPS.getProperty("employee.id"));
    }

    @Override
    public String getFirstNameEmployee() {
        return PROPS.getProperty("employee.firstName");
    }

    @Override
    public String getLastNameEmployee() {
        return PROPS.getProperty("employee.lastName");
    }

    @Override
    public String getMiddleNameEmployee() {
        return PROPS.getProperty("employee.middleName");
    }

    @Override
    public String getEmailEmployee() {
        return PROPS.getProperty("employee.email");
    }

    @Override
    public String getUrlEmployee() {
        return PROPS.getProperty("employee.url");
    }

    @Override
    public String getPhoneEmployee() {
        return PROPS.getProperty("employee.phone");
    }

    @Override
    public String getBirthdateEmployee() {
        return PROPS.getProperty("employee.birthdate");
    }

    @Override
    public boolean isActiveEmployee() {
        return Boolean.parseBoolean(PROPS.getProperty("employee.isActive"));
    }

}
