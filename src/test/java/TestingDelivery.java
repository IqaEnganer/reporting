import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.headless;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofSeconds;

public class TestingDelivery {
    String plusDay = DataGenerator.generateDatePlusDay(4, "dd.MM.yyyy");
    String rescheduling = DataGenerator.generateDatePlusDay(7, "dd.MM.yyyy");
    String minusDay = DataGenerator.generateDateMinusDay(1, "dd.MM.yyyy");


    @BeforeEach
    void SetUpAll() {
        headless = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("http://localhost:9999/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("Checking a successful appointment")
    public void shouldScheduleMeetingOnSpecifiedDate() {
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatorPhoneNumber("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button__content'] .button__text").click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(11));
        $("[data-test-id='success-notification']").shouldBe(text("?????????????? ?????????????? ?????????????????????????? ???? " + plusDay));
    }

    // ???????????????? ???????????????????????????????? ????????
    @Test
    @DisplayName("Checking rescheduling dates")
    public void shouldRescheduleMeetingOnSpecifiedDate() {
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatorPhoneNumber("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button__content'] .button__text").click();
        $("[data-test-id='success-notification']").shouldBe(text("?????????????? ?????????????? ?????????????????????????? ???? " + plusDay));
        // ???????????????????????????????? ????????
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(rescheduling);
        $("[class='button__content'] .button__text").click();
        $(new Selectors.WithText("?? ?????? ?????? ?????????????????????????? ?????????????? ???? ???????????? ????????. ???????????????????????????????")).shouldBe(visible);
        $("[class='button button_view_extra button_size_s button_theme_alfa-on-white'] .button__text").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(text("?????????????? ?????????????? ?????????????????????????? ???? " + rescheduling));


    }


    @Test
    @DisplayName("Checking with an invalid city")
    void shouldMessageAboutInvalidDataWillBeDisplayedInCityField() {
        $("[data-test-id=city] .input__control").setValue(DataGenerator.generatorCityNotValid());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='phone'] .input__control").setValue("+79287775566");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(withText("???????????????? ?? ?????????????????? ?????????? ????????????????????")).shouldBe(appear, ofSeconds(4));


    }

    @Test
    @DisplayName("Checking with a valid value in the field (full name)")
    void shouldMessageAboutInvalidDataWillBeDisplayedInNameField() {
        $("[data-test-id=city] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[class='input__control'][name='name']").setValue(DataGenerator.generatorNameNotValid());
        $("[data-test-id='phone'] .input__control").setValue("+79287775566");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(withText("?????? ?? ?????????????? ???????????????? ??????????????. ?????????????????? ???????????? ?????????????? ??????????, ?????????????? ?? ????????????.")).shouldBe(appear, ofSeconds(4));

    }


    @Test
    @DisplayName("Checking with an invalid value in the field (Phone number)")
    void shouldMessageAboutInvalidDataWillBeDisplayedInPhoneNumberField0() {
        $("[data-test-id=city] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[class='input__control'][name='name']").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatorNumberNotValid());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(withText("?????????????? ???????????? ??????????????. ???????????? ???????? 11 ????????, ????????????????, +79012345678.")).shouldBe(visible, ofSeconds(2));
    }


    @Test
    @DisplayName("Test with an empty field (Phone number)")
    void shouldMessageAboutInvalidDataWillBeDisplayedInPhoneNumberField1() {
        $("[data-test-id=city] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[class='input__control'][name='name']").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(withText("???????? ?????????????????????? ?????? ????????????????????")).shouldBe(visible, ofSeconds(2));
    }


    @Test
    @DisplayName("Test with an empty value in the field (Date)")
    void shouldMessageAboutInvalidDataWillBeDisplayedInDateField0() {
        $("[data-test-id=city] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys("BACKSPACE");
        $("[class='input__control'][name='name']").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatorPhoneNumber("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(withText("?????????????? ?????????????? ????????")).shouldBe(appear, ofSeconds(3));
    }


    @Test
    @DisplayName("Test with an invalid value in the field (Date)")
    void shouldMessageAboutInvalidDataWillBeDisplayedInDateField1() {
        $("[data-test-id=city] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(minusDay);
        $("[class='input__control'][name='name']").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(withText("?????????? ???? ?????????????????? ???????? ????????????????????")).shouldBe(appear, ofSeconds(3));
    }


    @Test
    @DisplayName("Test without confirmation of the data processing agreement (Check the box)")
    void shouldShowTheRequiredItemMessageAsAConsentFlag() {
        $("[class='input__control'][name='name']").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generatorCity());
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatorPhoneNumber("ru"));
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(".input_invalid[data-test-id='agreement']").shouldBe(visible);
    }

    @Test
    @DisplayName("A test to check the drop-down list of cities")
    void showDropDownListAndSelectionOption() {
        // ???????? 2 ?????????? ?? ???????? ??????????
        $("[data-test-id=city] .input__control").setValue("????");
        // ?????????????? ???? ???????????? ???? ?????????????????????? ????????????
        $(withText("????????????")).click();
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys("BACKSPACE");
        $("[data-test-id='date'] .input__control").setValue(plusDay);
        $("[data-test-id='name'] .input__control").setValue(DataGenerator.generateFullName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(DataGenerator.generatorPhoneNumber("ru"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $(withText("??????????????!")).shouldBe(visible, ofSeconds(11));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(text("?????????????? ?????????????? ?????????????????????????? ???? " + plusDay));
    }
}