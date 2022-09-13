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

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.nio.channels.SocketChannel.open;

public class WebTestForm {

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
    //Разобраться с датами!
    @Test
    void testForm() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("17.09.2022");
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Успешно!')]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[contains(text(),'Встреча успешно забронирована на')]").should(Condition.visible, Duration.ofSeconds(15));
    }
    @Test
    void testFormWithoutCheckbox() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("17.09.2022");
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $(".button__content").click();
        $x("//*[contains(text(),'Я соглашаюсь с условиями обработки и использования моих персональных данных')]");
    }

    @Test
    void testFormName() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("17.09.2022");
        $("[name='name']").setValue("Аленин-Огарков Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Успешно!')]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[contains(text(),'Встреча успешно забронирована на')]").should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void testFormWrongCity() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("НьюЙорк");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("17.09.2022");
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Доставка в выбранный город недоступна')]");
    }

    @Test
    void testFormWrongDate() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("10.09.2022");
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]");
    }

    @Test
    void testFormWrongName() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("17.09.2022");
        $("[name='name']").setValue("Alenin Andrew");
        $("[name='phone']").setValue("+79046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]");
    }

    @Test
    void testFormWrongPhoneNumber() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("17.09.2022");
        $("[name='name']").setValue("Аленин Андрей");
        $("[name='phone']").setValue("89046472030");
        $("[role='presentation']").click();
        $(".button__content").click();
        $x("//*[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]");
    }

    //пустая форма без чек бокса
    @Test
    void testFormEmptyWithCheckbox() {
        Selenide.open("http://localhost:9999");
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
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("");
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("");
        $(".button__content").click();
        $x("//*[contains(text(),'Поле обязательно для заполнения')]");
    }
}
