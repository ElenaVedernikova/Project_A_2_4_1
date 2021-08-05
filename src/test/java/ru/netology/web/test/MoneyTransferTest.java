package ru.netology.web.test;

import lombok.val;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferFirstToSecondCard() {
        int amount = 2000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardBalanceFirst = dashboard.getFirstCardBalance();
        val cardBalanceSecond = dashboard.getSecondCardBalance();
        val cardInfo = DataHelper.Card.getFirstCardInfo();
        val transferMoney = dashboard.secondCardButton();
        transferMoney.moneyTransfer(cardInfo, amount);
        val balanceAfterSendFirst = DataHelper.Card.balanceAfterSend(cardBalanceFirst, amount);
        val balanceAfterSendSecond = DataHelper.Card.balanceAfterGet(cardBalanceSecond, amount);
        assertEquals(balanceAfterSendFirst, dashboard.getFirstCardBalance());
        assertEquals(balanceAfterSendSecond, dashboard.getSecondCardBalance());
    }

    @Test
    void shouldTransferSecondToFirstCard() {
        int amount = 2000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardBalanceFirst = dashboard.getFirstCardBalance();
        val cardBalanceSecond = dashboard.getSecondCardBalance();
        val cardInfo = DataHelper.Card.getSecondCardInfo();
        val transferMoney = dashboard.firstCardButton();
        transferMoney.moneyTransfer(cardInfo, amount);
        val balanceAfterSendFirst = DataHelper.Card.balanceAfterGet(cardBalanceFirst, amount);
        val balanceAfterSendSecond = DataHelper.Card.balanceAfterSend(cardBalanceSecond, amount);
        assertEquals(balanceAfterSendFirst, dashboard.getFirstCardBalance());
        assertEquals(balanceAfterSendSecond, dashboard.getSecondCardBalance());
    }

    @Test
    void shouldReturnErrorMessageToSendMoreThanHave() {
        int amount = 15000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardInfo = DataHelper.Card.getSecondCardInfo();
        val transferMoney = dashboard.firstCardButton();
        transferMoney.moneyTransfer(cardInfo, amount);
        transferMoney.errorMessageForBalance();
    }

    @Test
    void shouldReturnErrorMessageForWrongCardNumber() {
        int amount = 1500;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardInfo = DataHelper.Card.getWrongCardInfo();
        val transferMoney = dashboard.firstCardButton();
        transferMoney.moneyTransfer(cardInfo, amount);
        transferMoney.errorMessageForCard();
    }
}

