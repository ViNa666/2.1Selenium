import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderCardTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldSendOrderCard() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Иван Римский-Корсаков");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79009999999");
        driver.findElement(By.cssSelector("[data-test-id ='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector("button .button__text")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        Assertions.assertEquals(expectedText, actualText);


    }

    @Test
    public void shouldSendOrderCardIfNameIsInvalid() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79009999999");
        driver.findElement(By.cssSelector("[data-test-id ='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector("button .button__text")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    public void shouldSendOrderCardIfPhoneIsInvalid() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Иван Римский-Корсаков");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("555-00-00");
        driver.findElement(By.cssSelector("[data-test-id ='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector("button .button__text")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    public void shouldSendOrderCardIfNameIsEmpty() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79009999999");
        driver.findElement(By.cssSelector("[data-test-id ='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector("button .button__text")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    public void shouldSendOrderCardIfPhoneIsEmpty() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Иван Римский-Корсаков");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id ='agreement'] .checkbox__box")).click();
        driver.findElement(By.cssSelector("button .button__text")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    public void shouldSendOrderCardIfCheckboxIsUnchecked() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Иван Римский-Корсаков");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("");

        driver.findElement(By.cssSelector("button .button__text")).click();

        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isDisplayed());





    }



}
