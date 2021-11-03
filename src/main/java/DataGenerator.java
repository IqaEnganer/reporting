import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@Data
@NoArgsConstructor
public class DataGenerator {
    // Переменные хранящие в себе данные для вызова в тестах
    String rescheduleDate = generateDatePlusDay(7, "dd.MM.yyyy");
    String plusDay = generateDatePlusDay(4, "dd.MM.yyyy");
    String minusDay = generateDateMinusDay(1, "dd.MM.yyyy");
    String fullNameRu = generateFullName("ru");
    String city = generatorCity("ru");
    String phoneNumber = generatorPhoneNumber("ru");
    String notValidatePhoneNumb = "+7928";

    // Метод принимающий формат и дни чтобы их сложить с текущей датой
    private String generateDatePlusDay(int days, String formatPattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    // Метод принимающий формат и дни чтобы их вычесть от текущей датой
    private String generateDateMinusDay(int days, String formatPattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    // Метод для генерации ФИО
    private String generateFullName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String fullName = faker.name().fullName();
        return fullName;
    }

    // Метод для генерации определенных городов в которые осуществляется доставка
    private String generatorCity(String locale) {
        String[] cities = {"Кемерово", "Майкоп", "Москва", "Симферополь", "Смоленск", "Тамбов", "Киров", "Красноярск"};
        Random index = new Random();
        int indexInt = index.nextInt(cities.length);
        String randomCity = cities[indexInt];
        return randomCity;

        //  Faker faker = new Faker(new Locale(locale));
        //  String city = faker.address().cityName();
        //  return city;
    }

    // Метод для генерации номера телефона
    private String generatorPhoneNumber(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phoneNumber = faker.phoneNumber().phoneNumber();
        return phoneNumber;
    }


}
