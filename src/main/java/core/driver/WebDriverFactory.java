package core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Objects;

public class WebDriverFactory implements AutoCloseable {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<String> browserName = new ThreadLocal<>();

    public static synchronized void setDriver(String browser, boolean headless) {
        Objects.requireNonNull(browser, "Browser parameter cannot be null");

        if (driver.get() != null) {
            throw new IllegalStateException("WebDriver is already initialized for this thread");
        }

        try {
            WebDriver webDriver = createDriver(browser, headless);
            configureDriver(webDriver);
            driver.set(webDriver);
            browserName.set(browser); // Store per-thread browser name
        } catch (Exception e) {
            throw new WebDriverInitializationException("Failed to initialize WebDriver", e);
        }
    }

    private static WebDriver createDriver(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> createChromeDriver(headless);
            // Add other browsers here
            default -> throw new IllegalArgumentException("Browser not supported: " + browser);
        };
    }

    private static ChromeDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
        }
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private static void configureDriver(WebDriver webDriver) {
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new IllegalStateException("WebDriver has not been initialized for this thread");
        }
        return webDriver;
    }

    public static String getBrowserName() {
        String browser = browserName.get();
        return browser != null ? browser : "Unknown";
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (Exception e) {
                e.printStackTrace(); // Optional: log properly
            } finally {
                driver.remove();
                browserName.remove();
            }
        }
    }

    @Override
    public void close() {
        quitDriver();
    }

    public static class WebDriverInitializationException extends RuntimeException {
        public WebDriverInitializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
