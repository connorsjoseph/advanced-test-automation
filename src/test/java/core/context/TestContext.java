package core.context;

import core.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;
import java.time.Duration;
import org.openqa.selenium.Capabilities;

public class TestContext implements AutoCloseable {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<String> browserThread = new ThreadLocal<>();
    private final ConfigReader configReader;

    public TestContext() {
        this.configReader = new ConfigReader();
    }

    public void initializeDriver(String browser) {
        try {
            browserThread.set(browser.toLowerCase());
            boolean isHeadless = Boolean.parseBoolean(configReader.getConfigValue("headless"));
            String gridUrl = configReader.getConfigValue("grid.url");

            WebDriver driver = switch (browserThread.get()) {
                case "chrome" -> createRemoteDriver(gridUrl, createChromeOptions(isHeadless));
                case "firefox" -> createRemoteDriver(gridUrl, createFirefoxOptions(isHeadless));
                case "edge" -> createRemoteDriver(gridUrl, createEdgeOptions(isHeadless));
                default -> throw new IllegalArgumentException("Browser " + browser + " not supported");
            };

            configureDriver(driver);
            driverThread.set(driver);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
        }
    }

    private void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }

    public WebDriver getWebDriver() {
        WebDriver driver = driverThread.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    public String getBrowserName() {
        return browserThread.get();
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }

    @Override
    public void close() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error while closing WebDriver: " + e.getMessage());
            } finally {
                driverThread.remove();
                browserThread.remove();
            }
        }
    }

    private WebDriver createRemoteDriver(String gridUrl, Object options) throws MalformedURLException {
        Capabilities capabilities = (Capabilities) options;

        int maxAttempts = 3;
        int attempt = 0;
        Exception lastException = null;

        while (attempt < maxAttempts) {
            try {
                System.out.println("Attempting to create RemoteWebDriver session (attempt " + (attempt + 1) + ")");
                return new RemoteWebDriver(new URL(gridUrl), capabilities);
            } catch (Exception e) {
                lastException = e;
                attempt++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        throw new RuntimeException("Failed to create RemoteWebDriver after " + maxAttempts + " attempts. Last error: " +
                (lastException != null ? lastException.getMessage() : "unknown"), lastException);
    }

    private ChromeOptions createChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    private FirefoxOptions createFirefoxOptions(boolean isHeadless) {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless) {
            options.addArguments("--headless");
        }
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return options;
    }

    private EdgeOptions createEdgeOptions(boolean isHeadless) {
        EdgeOptions options = new EdgeOptions();
        if (isHeadless) {
            options.addArguments("--headless");
        }
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return options;
    }
}