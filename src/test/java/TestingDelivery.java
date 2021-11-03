import com.codeborne.selenide.selector.WithText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestingDelivery {
    DataGenerator generator = new DataGenerator();


    @BeforeEach
    void SetUpAll() {
        open("http://localhost:9999/");
        //Configuration.headless = true;

    }
    // Проверка успешной назначенной встречи
    @Test
    public void shouldScheduleMeetingOnSpecifiedDate() {
        $("[data-test-id='name'] .input__control").setValue(generator.fullNameRu);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(generator.plusDay);
        $("[data-test-id='city'] .input__control").setValue(generator.city);
        $("[data-test-id='phone'] .input__control").setValue(generator.phoneNumber);
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button__content'] .button__text").click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(11));
        $("[data-test-id='success-notification']").shouldBe(text("Встреча успешно запланирована на " + generator.plusDay));
    }
    // Проверка перепланирования даты
    @Test
    public void shouldRescheduleMeetingOnSpecifiedDate() {
        $("[data-test-id='name'] .input__control").setValue(generator.fullNameRu);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(generator.plusDay);
        $("[data-test-id='city'] .input__control").setValue(generator.city);
        $("[data-test-id='phone'] .input__control").setValue(generator.phoneNumber);
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button__content'] .button__text").click();
        // Перепланирование даты
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(generator.rescheduleDate);
        $("[class='button__content'] .button__text").click();
        $(new WithText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
        $("[class='button button_view_extra button_size_s button_theme_alfa-on-white'] .button__text").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(text("Встреча успешно запланирована на " + generator.rescheduleDate));


    }

}