package database.company;

import utils.FakeTestData;

import java.util.Random;

public enum LocalCompanyService implements CompanyService{
    LOCAL_COMPANY_SERVICE;

    @Override
    public boolean getIsActiveCompany() {
        return new Random().nextBoolean();
    }

    @Override
    public String getNameCompany() {
        return FakeTestData.nameCompany;
    }

    @Override
    public String getDescriptionCompany() {
        return FakeTestData.descriptionCompany;
    }
}
