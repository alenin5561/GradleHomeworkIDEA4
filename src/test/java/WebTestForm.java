import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.SocketAddress;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.nio.channels.SocketChannel.open;

public class WebTestForm {

    WebDriver driver;
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String planningDate = generateDate(4);

    @BeforeEach
    void setupTest(){
        Selenide.open("http://localhost:9999");
        }

    @Test
    void testForm() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        String planningDate = generateDate(4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Успешно!')]").should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
    @Test
    void testFormWithoutCheckbox() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $(".button__content").click();
        $x("//*[contains(text(),'Я соглашаюсь с условиями обработки и использования моих персональных данных')]");
    }

    @Test
    void testFormName() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Аленин-Огарков Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Успешно!')]").should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void testFormWrongCity() {
        $("[placeholder='Город']").setValue("НьюЙорк");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Доставка в выбранный город недоступна')]");
    }

    @Test
    void testFormWrongDate() {
        String planningDate = generateDate(1);
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]");
    }

    @Test
    void testFormWrongName() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Alenin Andrew");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]");
    }

    @Test
    void testFormWrongPhoneNumber() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("89046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]");
    }

    @Test
    void testFormEmptyWithCheckbox() {
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("");
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Поле обязательно для заполнения')]");
    }

    @Test
    void testFormEmptyWithoutCheckbox() {
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("");
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("");
        $(".button__content").click();
        $x("//*[contains(text(),'Поле обязательно для заполнения')]");
    }
}
