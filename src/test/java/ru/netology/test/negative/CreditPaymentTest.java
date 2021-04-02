package ru.netology.test.negative;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Card;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class CreditPaymentTest {
    @BeforeAll
    static void reporting() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void openWeb() {
        open("http://localhost:8080");
        val mainPage = new MainPage();
        mainPage.chooseBuyCredit();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //Поле "Номер карты"
    @Test
    void notBuyATripInvalidNumberCard() {
        Card card = new Card(getInValidNumber(), getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getErrorNotification();
    }

    @Test
    void notBuyATripShortCardNumber() {
        Card card = new Card(getShortNumber(), getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidFormatNotification();
    }

    @Test
    void notBuyATripEmptyCardNumber() {
        Card card = new Card("", getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //Поле "Месяц"
    //TODO:баг ошибку не выдает
    @Test
    void notBuyInvalidMonth() {
        Card card = new Card(getApprovedNumber(), "00", getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //TODO:баг не та ошибка
    @Test
    void notBuyEmptyMonth() {
        Card card = new Card(getApprovedNumber(), "", getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //TODO: ошибочным подсвечено только поле "год"
    @Test
    void notBuyCardExpired() {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getLastYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getCardExpiredNotification();
    }

    //Поле "Год"
    @Test
    void notBuyLastYearCard() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getLastYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getCardExpiredNotification();
    }

    //баг не та ошибка
    @Test
    void notBuyEmptyFieldYear() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), "", getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldRequiredNotification();
    }

    //Поле "Владелец"
    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyFieldCardHolderOnlyNameInLatinaLetter() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getOnlyNameInLatinLetters(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFullNameNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyFieldCardHolderOnlySurname() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getOnlyLastName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFullNameNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyFirstNameAndLastNameAtHyphen() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getOnlyName()
                + "-" + getOnlyLastName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidFormatNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameMore100Letters() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getLongName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldSymbolRestrictionNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameNumbersInsteadLetters() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getNameWithNumber(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldCanOnlyContainLettersNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameOneLetter() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(),
                getNameWithOneLetter(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldMustContainMoreThanOneLetterNotification();
    }

    //TODO: не та ошибка
    @Test
    void notBuyCardHolderNameSpace() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), " ", getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldCanOnlyContainLettersNotification();
    }

    //TODO: оплата проходит, ошибки нет
    @Test
    void notBuyCardHolderNameSpanInsteadName() {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(),
                " " + getOnlyLastName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getFieldCanOnlyContainLettersNotification();
    }

    @Test
    void notBuyEmptyFieldCardHolderName() {
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

    //TODO: все поля, кроме Владелец подсвечены не той ошибкой
    @Test
    void notBuyEmptyApplication() {
        Card card = new Card();
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getAllFieldsAreRequired();
    }



}
