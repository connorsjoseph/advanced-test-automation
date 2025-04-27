package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.context.TestContext;
import core.logging.LoggerConfig;

public class Hooks {
    private final TestContext context;
    private static final ThreadLocal<String> currentBrowser = new ThreadLocal<>();
    private static final ThreadLocal<Logger> loggerThread = new ThreadLocal<>();

    public Hooks(TestContext context) {
        this.context = context;
        // Initialize with a default logger, will be replaced per scenario
        loggerThread.set(LoggerConfig.getLogger(Hooks.class));
    }

    @Before
    public void setUp(Scenario scenario) {
        // Configure and get a logger specific to this test scenario
        LoggerConfig.configureLoggerForTest(scenario.getName());
        Logger logger = LoggerFactory.getLogger(Hooks.class.getName() + "." + Thread.currentThread().getId());
        loggerThread.set(logger);

        logger.info("🚀 Starting scenario: {} [Thread: {}]", scenario.getName(), Thread.currentThread().getId());

        try {
            // Get browsers from config
            String[] browsers = context.getConfigReader().getBrowsersList();

            // Check if browsers array is empty
            if (browsers.length == 0) {
                throw new IllegalStateException("No browsers configured for testing");
            }

            // Round-robin browser selection for parallel execution
            long threadId = Thread.currentThread().getId();
            String browser = browsers[(int) (threadId % browsers.length)];

            try {
                // Initialize driver with selected browser
                context.initializeDriver(browser);
                currentBrowser.set(browser);

                logger.info("Browser initialized: {} for scenario: {}", browser, scenario.getName());
            } catch (Exception e) {
                // Clean up ThreadLocal in case of initialization failure
                currentBrowser.remove();
                throw e;
            }
        } catch (Exception e) {
            logger.error("❌ Failed to initialize WebDriver: {}", e.getMessage(), e);
            throw e;
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        Logger logger = loggerThread.get();

        try {
            WebDriver driver = context.getWebDriver();
            if (scenario.isFailed() && driver != null) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                    // Safe access to currentBrowser
                    String browserName = currentBrowser.get();
                    String screenshotName = "Screenshot-" + (browserName != null ? browserName : "unknown");

                    scenario.attach(screenshot, "image/png", screenshotName);
                    logger.error("❌ Scenario failed: {} [Thread: {}]",
                            scenario.getName(), Thread.currentThread().getId());
                } catch (Exception e) {
                    logger.warn("⚠️ Failed to capture screenshot: {}", e.getMessage(), e);
                }
            } else {
                logger.info("✅ Scenario completed successfully: {} [Thread: {}]",
                        scenario.getName(), Thread.currentThread().getId());
            }
        } finally {
            try {
                context.close();
                logger.info("Browser closed successfully [Thread: {}]", Thread.currentThread().getId());
            } catch (Exception e) {
                logger.warn("⚠️ Failed to close browser: {}", e.getMessage(), e);
            } finally {
                // Always clean up thread locals
                currentBrowser.remove();
                loggerThread.remove();
            }
        }
    }
}