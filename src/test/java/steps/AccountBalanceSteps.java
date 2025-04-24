package steps;

import core.context.TestContext;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import ui.pages.AccountBalancePage;

import static org.testng.Assert.assertTrue;

public class AccountBalanceSteps {

    private WebDriver driver;
    private AccountBalancePage accountBalancePage;

    // Update constructor to pass TestContext to AccountBalancePage
    public AccountBalanceSteps(TestContext context) {
        this.driver = context.getWebDriver();
        this.accountBalancePage = new AccountBalancePage(context); // Passing context here instead of driver
    }

   /* @Given("user is logged in")
    public void user_is_logged_in() {
        // Assuming login is already performed in another step
    }*/

    @When("user navigates to the account balance section")
    public void user_navigates_to_account_balance_section() {
        accountBalancePage.navigateToAccountBalance();
    }

    @Then("user should see the correct account balance")
    public void user_should_see_correct_account_balance() {
        assertTrue(accountBalancePage.isAccountBalanceDisplayed());
    }
}
