package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.*;

public class MainPage {
    private final SelenideElement heading = $$("h2").findBy(text("Путешествие дня"));
    private final SelenideElement paymentButton = $$("button.button").findBy(text("Купить"));
    private final SelenideElement creditButton = $$("button.button").findBy(text("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);
    }

    public PaymentPage chooseBuy() {
        paymentButton.click();
        return new PaymentPage();
    }

    public PaymentPage chooseBuyCredit() {
        creditButton.click();
        return new PaymentPage();
    }
}
