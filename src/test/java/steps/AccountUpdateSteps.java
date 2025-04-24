package steps;

import core.context.TestContext;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import ui.pages.AccountUpdatePage;
import static org.testng.Assert.assertTrue;

public class AccountUpdateSteps {

    private WebDriver driver;
    private AccountUpdatePage accountUpdatePage;

    public AccountUpdateSteps(TestContext context) {
        this.driver = context.getWebDriver();  // Optional if you use it elsewhere
        this.accountUpdatePage = new AccountUpdatePage(context);  // ✅ correct type
    }


  /*  @Given("user is logged in")
    public void user_is_logged_in() {
        // Assuming login is already performed in another step
    }*/

    @When("user updates the email address to {string}")
    public void user_updates_email(String email) {
        accountUpdatePage.updateEmail(email);  // Delegate to AccountUpdatePage
    }

    @When("user updates the phone number to {string}")
    public void user_updates_phone_number(String phoneNumber) {
        accountUpdatePage.updatePhoneNumber(phoneNumber);  // Delegate to AccountUpdatePage
    }

    @And("clicks the save button")
    public void clicks_save_button() {
        accountUpdatePage.saveChanges();  // Delegate to AccountUpdatePage
    }

    @Then("the email address should be updated successfully")
    public void email_should_be_updated() {
        assertTrue(accountUpdatePage.isEmailUpdated());  // Check success via AccountUpdatePage
    }

    @Then("the phone number should be updated successfully")
    public void phone_number_should_be_updated() {
        assertTrue(accountUpdatePage.isPhoneNumberUpdated());  // Check success via AccountUpdatePage
    }
}
