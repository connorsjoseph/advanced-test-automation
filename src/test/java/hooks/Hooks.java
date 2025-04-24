package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import core.context.TestContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        // Any future setup logic can go here
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = context.getWebDriver();

        try {
            if (driver != null && scenario.isFailed()) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Screenshot on failure");
                    System.err.println("❌ Scenario failed: " + scenario.getName());
                } catch (Exception e) {
                    System.err.println("⚠️ Failed to capture screenshot: " + e.getMessage());
                }
            }
        } finally {
            try {
                context.close(); // Using close() instead of cleanup()
            } catch (Exception e) {
                System.err.println("⚠️ Failed to close browser: " + e.getMessage());
            }
        }
    }
}