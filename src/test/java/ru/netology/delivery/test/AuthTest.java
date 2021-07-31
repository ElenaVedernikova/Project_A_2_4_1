package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.delivery.data.DataGenerator.Registration.getUser;
import static ru.netology.delivery.data.DataGenerator.getRandomLogin;
import static ru.netology.delivery.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(exactText("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] .input__control").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] .input__control").setValue(wrongLogin);
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}

