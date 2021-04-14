package ru.netology.test.negative;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Card;
import ru.netology.data.DBUtils;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class PaymentTest {
    @BeforeAll
    static void reporting() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        DBUtils.clearTables();
        val mainPage = new MainPage();
        mainPage.chooseBuy();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    //Поле "Номер карты"
    @Test
    void notBuyATripInvalidNumberCard () {
        Card card = new Card(getInValidNumber(), getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getErrorNotification();
    }

    @Test
    void notBuyATripShortCardNumber () {
        Card card = new Card(getShortNumber(), getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidFormatNotification();
    }

    @Test
    void notBuyATripEmptyCardNumber () {
        Card card = new Card( "", getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //Поле "Месяц"
    //TODO: надпись под полем "Месяц" должно содержать текст: "Неверно указан срок действия карты"
    @Test
    void notBuyInvalidMonth () {
        Card card = new Card(getApprovedNumber(), "00", getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //TODO: Надпись под полем "Месяц" должно содержать текст: "Поле обязательно для заполнения"
    @Test
    void notBuyEmptyMonth () {
        Card card = new Card(getApprovedNumber(), "", getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    @Test
    void notBuyCardExpired () {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getCurrentYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getDeadlineIncorrectlyNotification();
    }

    //Поле "Год"
    @Test
    void notBuyLastYearCard () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getLastYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getCardExpiredNotification();
    }

    //TODO: Надпись под полем "Год" должна содержать текст "Поле обязательно для заполнения"
    @Test
    void notBuyEmptyFieldYear () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), "", getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //Поле "Владелец"
    //TODO: оплата проходит, надпись под полем должна содержать текст "Введите полное имя и фамилию"
    @Test
    void notBuyFieldCardHolderOnlyName () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getOnlyName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFullNameNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyFieldCardHolderOnlySurnameInLatinLetters () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getOnlyLastNameInLatinLetters(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFullNameNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyFirstNameAndLastNameAtHyphen () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getOnlyName()
                + "-" + getOnlyLastName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidFormatNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameMore100Letters () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getLongName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldSymbolRestrictionNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameNumbersInsteadLetters () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getNameWithNumber(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldCanOnlyContainLettersNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameOneLetter () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(),
                getNameWithOneLetter(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldMustContainMoreThanOneLetterNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameSpanInsteadLastName () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(),
                getOnlyName() + " ", getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldCanOnlyContainLettersNotification();
    }

    @Test
    void notBuyEmptyFieldCardHolderName () {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), "", getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //Поле "CVC/CVV"
    //TODO: не та ошибка
    @Test
    void notBuyFieldCVCOneNumber() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(),
                getValidName(), getCVCWithOneDigit());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldMustContainThreeDigitNotification();
    }

    //TODO: не та ошибка
    @Test
    void notBuyFieldCVCTwoNumber() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(),
                getValidName(), getCVCWithTwoDigit());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldMustContainThreeDigitNotification();
    }

    //TODO: не та ошибка, доп ошибка в поле Владелец
    @Test
    void notBuyEmptyFieldCVC() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(),
                getValidName(), "");
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //Пустая заявка
    //TODO: все поля, кроме Владелец подсвечены не той ошибкой
    @Test
    void notBuyEmptyApplication() {
        Card card = new Card();
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getAllFieldsAreRequired();
    }
}
