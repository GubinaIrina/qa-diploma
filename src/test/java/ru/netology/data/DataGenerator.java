package ru.netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

public class DataGenerator {
    public static Faker faker = new Faker(new Locale("en"));

    public static String getApprovedNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInValidNumber() {
        return "4444 4444 4444 4443";
    }

    public static String getShortNumber() {
        return "4444 4444 4444 44";
    }

    public static String getNextMonth() {
        LocalDate nextMonth = LocalDate.now();
        return nextMonth.plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getLastMonth() {
        LocalDate lastMonth = LocalDate.now();
        return lastMonth.minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getFutureYear() {
        LocalDate futureYear = LocalDate.now();
        return futureYear.plusYears(2).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getCurrentYear() {
        LocalDate currentYear = LocalDate.now();
        return currentYear.format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getLastYear() {
        LocalDate lastYear = LocalDate.now();
        return lastYear.minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getValidNameInLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOnlyName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName();
    }

    public static String getOnlyNameInLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String getOnlyLastName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName();
    }

    public static String getOnlyLastNameInLatinLetters() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName();
    }

    public static String getLongName() {
        return faker.lorem().fixedString(101);
    }

    public static String getNameWithNumber() {
        return faker.number().digits(5);
    }

    public static String getNameWithOneLetter() {
        return faker.lorem().characters(1);
    }

    public static String getValidCVC() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("###");
    }

    public static String getCVCWithTwoDigit() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("##");
    }

    public static String getCVCWithOneDigit() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("#");
    }
}
