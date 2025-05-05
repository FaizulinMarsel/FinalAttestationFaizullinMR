package config.employee.request;

import config.ProjectConfig;

import java.util.Properties;

public class TestData {
    public static final Properties PROPS =  ProjectConfig.readProperties(ProjectConfig.Env.TEST_DATA);
    public int getIncorrectCompanyId() {
        return Integer.parseInt(PROPS.getProperty("incorrect.company.id"));
    }

    public String getIncorrectUserToken() {
        return PROPS.getProperty("incorrect.user.token");
    }

    public String getIncorrectEmail() {
        return PROPS.getProperty("incorrect.employee.email");
    }

    public String getBrokenJson() {
        return PROPS.getProperty("incorrect.emloyee.json");
    }

    public int getIncorrectEmployeeId() {
        return Integer.parseInt(PROPS.getProperty("incorrect.employee.id"));
    }

    public String getIncorrectLastName() {
        return PROPS.getProperty("incorrect.emloyee.lastname");
    }
}
