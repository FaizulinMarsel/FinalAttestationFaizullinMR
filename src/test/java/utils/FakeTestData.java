package utils;

import com.github.javafaker.Faker;

import static org.apache.commons.lang3.StringUtils.truncate;

public class FakeTestData {
    private static final Faker FAKER = new Faker();

    public static String firstName = FAKER.name().firstName();
    public static String lastName = FAKER.name().lastName();
    public static String postalCode = FAKER.address().city();

    public static String login = truncate(FAKER.name().name(),20);

    public static String password = truncate(FAKER.internet().password(),20);

    public static String nameCompany = truncate(FAKER.company().name(),20);
    public static String descriptionCompany = truncate(FAKER.company().catchPhrase(),20);

}
