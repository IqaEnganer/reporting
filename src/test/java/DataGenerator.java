import com.github.javafaker.Faker;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {

    }

    // Метод принимающий формат и дни чтобы их сложить с текущей датой
    public static String generateDatePlusDay(int days, String formatPattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    // Метод принимающий формат и дни чтобы их вычесть от текущей даты
    public static String generateDateMinusDay(int days, String formatPattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    // Метод для генерации ФИО
    public static String generateFullName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String fullName = faker.name().fullName();
        return fullName;
    }

    // Метод для генерации определенных городов в которые осуществляется доставка
    public static String generatorCity() {
        String[] cities = {"Кемерово", "Майкоп", "Москва", "Симферополь", "Смоленск", "Тамбов", "Киров", "Красноярск"};
        Random index = new Random();
        int indexInt = index.nextInt(cities.length);
        String randomCity = cities[indexInt];
        return randomCity;
    }

    // Метод для выбора не валидных городов
    public static String generatorCityNotValid() {
        String[] cities = {"Sochi", "Сочи", "Kirov"};
        Random index = new Random();
        int indexInt = index.nextInt(cities.length);
        String randomCity = cities[indexInt];
        return randomCity;
    }

    // Метод для не валидных рандомных значений в ФИО
    public static String generatorNameNotValid() {
        String[] cities = {"Semen Semenov", "Uchiha", "Привет", "П"};
        Random index = new Random();
        int indexInt = index.nextInt(cities.length);
        String randomCity = cities[indexInt];
        return randomCity;
    }

    // Метод для генерации номера телефона
    public static String generatorPhoneNumber(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phoneNumber = faker.phoneNumber().phoneNumber();
        return phoneNumber;
    }

    // Метод для не валидных телефонных номеров
    public static String generatorNumberNotValid() {
        String[] cities = {"+7928452625", "+7928", "+7"};
        Random index = new Random();
        int indexInt = index.nextInt(cities.length);
        String randomCity = cities[indexInt];
        return randomCity;
    }


}
