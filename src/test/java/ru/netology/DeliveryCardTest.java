package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryCardTest {

    @BeforeEach
    void setUp2() {
        open("http://localhost:9999");
    }
    public String meetingDate(int shift) {
        LocalDate newDate = LocalDate.now().plusDays(shift);
        return newDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    @Test
    public void successfulAppointmentBooking() {
        String meetingDate = meetingDate(4);
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Петров Петр Петрович");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15));
    }
    @Test
    public void shouldTownNotAdmCenter() {
        String meetingDate = meetingDate(4);
        $("[placeholder=\"Город\"]").setValue("Сочи");// заполнить поле городDateApp(4)
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Петров Петр Петрович");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Доставка в выбранный город недоступна")).should(visible);
    }

    @Test
    public void shouldTownNull() {
        String meetingDate = meetingDate(4);
        $("[placeholder=\"Город\"]").setValue("");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Петров Петр Петрович");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Поле обязательно для заполнения")).should(visible);
    }
    @Test
    public void shouldDateBefore3Days() {
        String meetingDate = meetingDate(2);
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Петров Петр Петрович");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Заказ на выбранную дату невозможен")).should(visible);
    }
    @Test
    public void shouldDateNull() {
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[name = \"name\"]").setValue("Петров Петр Петрович");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Неверно введена дата")).should(visible);
    }
    @Test
    public void shouldFIOIncorrect() {
        String meetingDate = meetingDate(5);
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Petrov Petr Petrovich");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).should(visible);
    }
    @Test
    public void shouldFIONull() {
        String meetingDate = meetingDate(5);
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Поле обязательно для заполнения")).should(visible);
    }
    @Test
    public void shouldPhoneIncorrect() {
        String meetingDate = meetingDate(5);
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Петров Петр Петрович");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+7111111111");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).should(visible);
    }
    @Test
    public void shouldPhoneNull() {
        String meetingDate = meetingDate(5);
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Петров Петр Петрович");//заполнить ФИО
        $("[name = \"phone\"]").setValue("");//заполнить телефон
        $("[data-test-id = \"agreement\"]").click();//нажать согласие
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $(byText("Поле обязательно для заполнения")).should(visible);
    }

    @Test
    public void shouldNotCheckbox() {
        String meetingDate = meetingDate(5);
        $("[placeholder=\"Город\"]").setValue("Краснодар");// заполнить поле город
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);// очистить дату
        $("[placeholder=\"Дата встречи\"]").setValue(meetingDate);// заполнить поле дата
        $("[name = \"name\"]").setValue("Вера-Мира У");//заполнить ФИО
        $("[name = \"phone\"]").setValue("+71111111111");//заполнить телефон
        $x("//*[text()=\"Забронировать\"]").click();//нажать забронировать
        $("checkbox_checked").should(hidden);
    }
}
