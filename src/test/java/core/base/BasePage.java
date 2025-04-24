package core.base;

import core.context.TestContext;
import core.utils.ConfigReader;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    protected ConfigReader configReader;

    public BasePage(TestContext context) {
        this.driver = context.getWebDriver();
        this.configReader = context.getConfigReader();
    }
}
