package qa.guru.allure.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import qa.guru.allure.pages.RegistrationFormPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import io.qameta.allure.selenide.AllureSelenide;
import helpers.Attach;
import static com.codeborne.selenide.Selenide.webdriver;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static io.qameta.allure.Allure.attachment;

public class RegistrationFormWithPageObjectsTests {
    RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    private static final String firstName, lastName, email, gender, mobile, day, month, year, hobbie, file, address, state, city;

    static {
        firstName = "Iuliia";
        lastName = "Kudrina";
        email = "rrr@mail.com";
        gender = "Other";
        mobile = "1234567890";
        day = "15";
        month = "July";
        year = "1997";
        hobbie = "Music";
        file = "Floppa.png";
        address = "My address";
        state = "NCR";
        city = "Delhi";
    }

    @BeforeAll
    static void configure() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }


    @Test
    @Feature("Форма на Demoqa.com")
    @Story("Заполнение и отправка формы")
    @Owner("juliafoxcat")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка формы на Demoqa.com")
    void fillFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открываем главную страницу", () -> {
        registrationFormPage.openPage();
        });
        step("Заполняем все поля", () -> {
        registrationFormPage.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setNumber(mobile)
                .setBirthDate(day, month, year)
                .setHobbies(hobbie)
                .setFile(file)
                .setAddress(address)
                .setPlace(state, city)
                .submitForm();
        });

        step("Проверяем все поля на совпадение данных", () -> {
            registrationFormPage.checkResultsTableVisible()
                    .checkResult("Student Name", firstName + " " + lastName)
                    .checkResult("Student Email", email)
                    .checkResult("Gender", gender)
                    .checkResult("Mobile", mobile)
                    .checkResult("Date of Birth", day + " " + month + "," + year)
                    .checkResult("Hobbies", hobbie)
                    .checkResult("Picture", file)
                    .checkResult("Address", address)
                    .checkResult("State and City", state + " " + city);
        });
        }
    @AfterEach
    void AddAttachments() {
        Attach.screenshotAs("Last screenshots");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

    }

