package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.util.data.RegistrationDto;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.util.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.util.DataGenerator.Registration.getUser;
import static ru.netology.util.DataGenerator.getRandomLogin;
import static ru.netology.util.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSuccessLoginIfRegisteredActiveUser() {
        RegistrationDto user = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldGetErrorIfNotRegisteredUser() {
        RegistrationDto user = getUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    public void shouldGetErrorIfRegisteredBlockedUser() {
        RegistrationDto user = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    public void shouldSuccessLoginIfWrongLogin() {
        RegistrationDto user = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(getRandomLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    public void shouldSuccessLoginIfWrongPassword() {
        RegistrationDto user = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
