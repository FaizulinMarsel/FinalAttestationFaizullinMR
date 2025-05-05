package utils;

import database.company.CompanyEntity;
import io.qameta.allure.Step;

import java.util.Random;

public class CompanyEntityCreator {

    public static CompanyEntity createCompany(){
        return new CompanyEntity(
                new Random().nextBoolean(),
                FakeTestData.nameCompany,
                FakeTestData.descriptionCompany
        );
    }
}
