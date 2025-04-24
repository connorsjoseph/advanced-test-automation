package steps;

import core.context.TestContext;
import core.manager.PageObjectManager;
import io.cucumber.java.en.Given;
import ui.pages.LoginPage;

public class CommonSteps {
    private final LoginPage loginPage;

    public CommonSteps(TestContext context) {
        this.loginPage = new PageObjectManager(context).getLoginPage();
    }

    @Given("user is logged in")
    public void user_is_logged_in() {
        // reuse your login flow
        loginPage.openLoginPage();
        loginPage.enterUsername("john");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();
        // you can assert dashboard visible here if you like
    }
}
