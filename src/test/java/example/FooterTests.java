package example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class FooterTests {
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void startBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    //Ожидание загрузки страницы
    public void waitForPageLoaded() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    //Ожидание видимости элемента
    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //Прокрутка вниз
    public void scrollToBottom() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    //Проверка футера
    public void checkFooterOnPage(String page, Consumer<WebElement> footerCheck) {
        driver.get(PageObjects.baseUrl + page);
        waitForPageLoaded();
        scrollToBottom();
        wait.until(ExpectedConditions.presenceOfElementLocated(PageObjects.FOOTER));

        WebElement footer = waitForVisible(PageObjects.FOOTER);
        footerCheck.accept(footer);
    }

    // ---------- ТЕСТЫ ----------

    @Order(1)
    @DisplayName("Проверка футера")
    @ParameterizedTest(name = "{0}")
    @MethodSource("example.PageObjects#pages")
    void testFooterExists(String page) {
        checkFooterOnPage(page, footer ->
                assertTrue(footer.isDisplayed(), "Футер не отображается"));
    }

    @Order(2)
    @DisplayName("Проверка Политики конфиденциальности")
    @ParameterizedTest(name = "{0}")
    @MethodSource("example.PageObjects#pages")
    void testPrivacyPolicyLink(String page) {
        checkFooterOnPage(page, footer -> {
            WebElement policyLink = footer.findElement(PageObjects.PRIVACY_POLICY_LINK);
            assertTrue(policyLink.getAttribute("href").contains(PageObjects.EXPECTED_POLICY_LINK),
                    "Ссылка 'Политика конфиденциальности' не отображается");
        });
    }

    @Order(3)
    @DisplayName("Проверка e-mail")
    @ParameterizedTest(name = "{0}")
    @MethodSource("example.PageObjects#pages")
    void testEmail(String page) {
        checkFooterOnPage(page, footer -> {
            WebElement email = footer.findElement(PageObjects.EMAIL_LINK);
            assertEquals(PageObjects.EXPECTED_EMAIL,
                    email.getAttribute("href"),
                    "E-mail в футере не соответствует ожидаемому");
        });
    }

    @Order(4)
    @DisplayName("Проверка телефона")
    @ParameterizedTest(name = "{0}")
    @MethodSource("example.PageObjects#pages")
    void testPhone(String page) {
        checkFooterOnPage(page, footer -> {
            WebElement phone = footer.findElement(PageObjects.PHONE_LINK);
            assertEquals(PageObjects.EXPECTED_PHONE,
                    phone.getAttribute("href"),
                    "Телефон в футере не соответствует ожидаемому");
        });
    }

    @Order(5)
    @DisplayName("Проверка Telegram")
    @ParameterizedTest(name = "{0}")
    @MethodSource("example.PageObjects#pages")
    void testTelegram(String page) {
        checkFooterOnPage(page, footer -> {
            WebElement tg = footer.findElement(PageObjects.TELEGRAM_LINK);
            assertEquals(PageObjects.EXPECTED_TELEGRAM,
                    tg.getAttribute("href"),
                    "Telegram в футере не соответствует ожидаемому");
        });
    }
}