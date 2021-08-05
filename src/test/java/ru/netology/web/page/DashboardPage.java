package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $(byText("Ваши карты"));
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public SelenideElement sumFirst = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    public SelenideElement sumSecond = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");
    private SelenideElement buttonTransferToFirst = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] [type='button']");
    private SelenideElement buttonTrnasferToSecond = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] [type='button']");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage firstCardButton() {
        buttonTransferToFirst.click();
        return new TransferPage();
    }

    public TransferPage secondCardButton() {
        buttonTrnasferToSecond.click();
        return new TransferPage();
    }

    public int getFirstCardBalance() {
        String valueFirst = sumFirst.getText();
        return extractBalance(valueFirst);
    }

    public int getSecondCardBalance() {
        String valueSecond = sumSecond.getText();
        return extractBalance(valueSecond);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
