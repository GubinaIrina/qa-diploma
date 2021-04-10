package ru.netology.test.positive;

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

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;

public class PaymentTest {
    @BeforeAll
    static void reporting() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void openWeb() {
        open("http://localhost:8080");
        DBUtils.clearTables();
        val mainPage = new MainPage();
        mainPage.chooseBuy();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void buyATripApprovedCard() throws SQLException {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getSuccessNotification();
        assertEquals("APPROVED", DBUtils.getPaymentStatus());
    }

    @Test
    void buyATripValidNameLatinLetters() throws SQLException {
        Card card = new Card(getApprovedNumber(), getNextMonth(), getFutureYear(), getValidNameInLatinLetters(),
                getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getSuccessNotification();
        assertEquals("APPROVED", DBUtils.getPaymentStatus());
    }

    //TODO: оплата не должна проходить
    @Test
    void notBuyATripDeclinedCard() throws SQLException {
        Card card = new Card(getDeclinedNumber(), getNextMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getErrorNotification();
        assertEquals("DECLINED", DBUtils.getPaymentStatus());
    }

    @Test
    void buyATripLastMonth() throws SQLException {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getFutureYear(), getValidName(), getValidCVC());
        val paymentPage = new PaymentPage();
        paymentPage.fullData(card);
        paymentPage.getSuccessNotification();
        assertEquals("APPROVED", DBUtils.getPaymentStatus());
    }
}