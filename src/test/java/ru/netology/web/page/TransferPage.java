package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement sum = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement sendButtton = $("[data-test-id='action-transfer']");

    public void moneyTransfer (DataHelper.Card card, int amount) {
        sum.setValue(String.valueOf(amount));
        from.setValue(card.getNumber());
        sendButtton.click();
    }

    public SelenideElement errorMessageForBalance() {
        return $(withText("У вас недостаточно средств для перевода такой суммы")).shouldBe(visible, Duration.ofSeconds(5));
    }

    public SelenideElement errorMessageForCard() {
        return $(withText("Ошибка!")).shouldBe(visible, Duration.ofSeconds(5));
    }
}

