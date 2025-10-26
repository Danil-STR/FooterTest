package example;

import org.openqa.selenium.By;
import java.util.stream.Stream;

public class PageObjects {

    public static final String baseUrl = "https://only.digital";

    //Список страниц для тестов
    public static Stream<String> pages() {
        return Stream.of("/", "/projects", "/company");
    }

    // Селекторы футера
    public static final By FOOTER = By.tagName("footer");
    public static final By PRIVACY_POLICY_LINK = By.cssSelector("a[href*='/pdf/Политика']");
    public static final By EMAIL_LINK = By.cssSelector("a[href^='mailto:']");
    public static final By PHONE_LINK = By.cssSelector("a[href^='tel:']");
    public static final By TELEGRAM_LINK = By.cssSelector("a[href*='t.me']");

    // Эталонные значения футера
    public static final String EXPECTED_POLICY_LINK = "https://only.digital/pdf/";
    public static final String EXPECTED_EMAIL = "mailto:hello@only.digital";
    public static final String EXPECTED_PHONE = "tel:+7 (495) 740 99 79";
    public static final String EXPECTED_TELEGRAM = "https://t.me/onlycreativedigitalagency";
}