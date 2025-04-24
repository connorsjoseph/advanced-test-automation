package ui.pages;

import core.base.BasePage;
import core.context.TestContext;
import core.utils.ConfigReader;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final ConfigReader config;

    // Locators
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.xpath("//input[@value='Log In']");

    public LoginPage(TestContext context) {
        super(context);
        this.config = context.getConfigReader();  // ✅ Get config from context
    }

    public void openLoginPage() {
        driver.get(config.getLoginUrl());  // ✅ This now works
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    public boolean isDashboardVisible() {
        // Update this locator with something unique on the dashboard page
        By dashboardHeader = By.xpath("//h1[contains(text(), 'Accounts Overview')]");
        return driver.findElement(dashboardHeader).isDisplayed();
    }

}
