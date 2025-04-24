package steps;

import core.context.TestContext;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import ui.pages.ChangePasswordPage;
import static org.testng.Assert.assertTrue;

public class ChangePasswordSteps {

    private WebDriver driver;
    private ChangePasswordPage changePasswordPage;

    public ChangePasswordSteps(TestContext context) {
        this.driver = context.getWebDriver();  // Optional, if you use it elsewhere
        this.changePasswordPage = new ChangePasswordPage(context);  // ✅ pass TestContext here
    }


    @Given("user is on the change password page")
    public void user_is_on_the_change_password_page() {
        driver.get("https://yourbankapp.com/change-password"); // Replace with actual URL
    }

    @When("user enters the old password {string} and the new password {string}")
    public void user_enters_passwords(String oldPassword, String newPassword) {
        changePasswordPage.enterOldPassword(oldPassword);
        changePasswordPage.enterNewPassword(newPassword);
        changePasswordPage.confirmNewPassword(newPassword);  // Confirming the new password
    }

    @And("clicks the change password button")
    public void clicks_change_password_button() {
        changePasswordPage.clickChangePasswordButton();
    }

    @Then("the password should be updated successfully")
    public void password_should_be_updated() {
        assertTrue(changePasswordPage.isPasswordChanged());  // Verify the success message
    }

    @Then("user should see an error message indicating incorrect old password")
    public void incorrect_old_password_message() {
        assertTrue(changePasswordPage.isErrorMessageDisplayed());  // Verify if error message is displayed
    }
}
