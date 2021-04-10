package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.time.Duration.ofSeconds;

public class PaymentPage {
    private SelenideElement fieldset = $("fieldset");
    private SelenideElement cardNumber = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $("input[placeholder='08']");
    private SelenideElement year = $("input[placeholder='22']");
    private SelenideElement cardHolder = $(byXpath("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[2]/input"));
    private SelenideElement cvc = $("input[placeholder='999']");
    private SelenideElement continueButton = $$("button").find(text("Продолжить"));
    private SelenideElement notification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");

    public PaymentPage() {
        fieldset.shouldBe(visible);
    }

    public void fullData(Card card) {
        cardNumber.setValue(card.getNumber());
        month.setValue(card.getMonth());
        year.setValue(card.getYear());
        cardHolder.setValue(card.getCardHolderName());
        cvc.setValue(card.getCvc());
        continueButton.click();
    }

    public void getSuccessNotification() {
        notification.shouldBe(visible, ofSeconds(20));
    }

    public void getErrorNotification() {
        errorNotification.shouldBe(visible, ofSeconds(20));
    }

    public void getInvalidFormatNotification() {
        $$(".input__sub").find(text("Неверный формат")).shouldBe(visible);
    }

    public void getFieldRequiredNotification() {
        $$(".input__sub").find(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void getCardExpiredNotification() {
        $$(".input__sub").find(text("Истёк срок действия карты")).shouldBe(visible);
    }

    public void getFullNameNotification() {
        $$(".input__sub").find(text("Введите полное имя и фамилию")).shouldBe(visible);
    }

    public void getFieldSymbolRestrictionNotification() {
        $$(".input__sub").find(text("Значение поля не может содержать более 100 символов")).shouldBe(visible);
    }

    public void getFieldCanOnlyContainLettersNotification() {
        $$(".input__sub").find(text("Значение поля может содержать только буквы и дефис")).shouldBe(visible);
    }

    public void getFieldMustContainMoreThanOneLetterNotification() {
        $$(".input__sub").find(text("Значение поля должно содержать больше одной буквы")).shouldBe(visible);
    }

    public void getFieldMustContainThreeDigitNotification() {
        $$(".input__sub").find(text("Значение поля должно содержать 3 цифры")).shouldBe(visible);
    }

    public void getAllFieldsAreRequired() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5)).find(text("Поле обязательно для заполнения"));
    }

    public void getDeadlineIncorrectlyNotification() {
        $$(".input__sub").find(text("Неверно указан срок действия карты")).shouldBe(visible);
    }
}
