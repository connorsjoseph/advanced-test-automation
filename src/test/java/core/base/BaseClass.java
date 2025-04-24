package core.base;

import core.context.TestContext;
import core.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {

    protected WebDriver driver;
    protected TestContext context;
    protected Logger logger;

    public BaseClass(TestContext context) {
        this.context = context;
        this.driver = context.getWebDriver();
        this.logger = LogManager.getLogger(this.getClass());
    }

    public void openUrl(String url) {
        logger.info("Navigating to URL: " + url);
        driver.get(url);
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title retrieved: " + title);
        return title;
    }

    public void tearDown() {
        WebDriverFactory.quitDriver();
        logger.info("Driver session terminated.");
    }
}
