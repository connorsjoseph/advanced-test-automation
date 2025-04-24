package steps;

import core.context.TestContext;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import ui.pages.LoginPage;

import static org.testng.Assert.assertTrue;

public class LoginSteps {

    private final WebDriver driver;
    private final LoginPage loginPage;
    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
        this.driver = context.getWebDriver();
        this.loginPage = new LoginPage(context);
    }

    @Given("user is on the login page")
    public void user_is_on_the_login_page() {
        driver.get(context.getConfigReader().getLoginUrl());
    }

    @When("user enters valid username {string} and password {string}")
    public void user_enters_valid_credentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("clicks the login button")
    public void clicks_login_button() {
        loginPage.clickLogin();
    }

    @Then("user should be navigated to the dashboard")
    public void user_should_be_navigated_to_dashboard() {
        assertTrue(loginPage.isDashboardVisible());
    }
}
