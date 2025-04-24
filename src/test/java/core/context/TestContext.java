package core.context;

import core.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.Capabilities;

public class TestContext implements AutoCloseable {
    private WebDriver driver;
    private final ConfigReader configReader;
    private String browserName;

    public TestContext() {
        this.configReader = new ConfigReader();
        initializeDriver();
    }

private void initializeDriver() {
    try {
        browserName = configReader.getConfigValue("browser").toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(configReader.getConfigValue("headless"));
        boolean isGridEnabled = Boolean.parseBoolean(configReader.getConfigValue("grid.enabled"));
        
        if (isGridEnabled) {
            String gridUrl = configReader.getConfigValue("grid.url");
            switch (browserName) {
                case "chrome" -> driver = createRemoteDriver(gridUrl, createChromeOptions(isHeadless));
                case "firefox" -> driver = createRemoteDriver(gridUrl, createFirefoxOptions(isHeadless));
                case "edge" -> driver = createRemoteDriver(gridUrl, createEdgeOptions(isHeadless));
                default -> throw new IllegalArgumentException("Browser " + browserName + " not supported");
            }
        } else {
            switch (browserName) {
                case "chrome" -> driver = new ChromeDriver(createChromeOptions(isHeadless));
                case "firefox" -> driver = new FirefoxDriver(createFirefoxOptions(isHeadless));
                case "edge" -> driver = new EdgeDriver(createEdgeOptions(isHeadless));
                default -> throw new IllegalArgumentException("Browser " + browserName + " not supported");
            }
        }
        configureDriver();
    } catch (MalformedURLException e) {
        throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
    }
}

    private WebDriver createRemoteDriver(String gridUrl, Object options) throws MalformedURLException {
    // Cast the options to Capabilities
    Capabilities capabilities = (Capabilities) options;
    
    // Add retry logic
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
                Thread.sleep(2000); // Wait 2 seconds before retrying
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    throw new RuntimeException("Failed to create RemoteWebDriver after " + maxAttempts + " attempts. Last error: " + 
        (lastException != null ? lastException.getMessage() : "unknown"), lastException);
}

    private void configureDriver() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }

    private ChromeOptions createChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Add these lines for better stability
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

    private SafariOptions createSafariOptions() {
        return new SafariOptions();
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public String getBrowserName() {
        return browserName;
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }

    @Override
    public void close() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error while closing WebDriver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }
}