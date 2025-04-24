package steps;

import core.context.TestContext;
import io.cucumber.java.en.*;
import ui.pages.FundTransferPage;

import static org.testng.Assert.assertTrue;

public class FundTransferSteps {

    private final FundTransferPage fundTransferPage;

    public FundTransferSteps(TestContext context) {
        this.fundTransferPage = new FundTransferPage(context);
    }

    @When("user enters the recipient account number {string} and the transfer amount {string}")
    public void user_enters_transfer_details(String accountNumber, String amount) {
        fundTransferPage.enterRecipientAccountNumber(accountNumber);
        fundTransferPage.enterTransferAmount(amount);
    }

    @And("clicks the transfer button")
    public void clicks_transfer_button() {
        fundTransferPage.clickTransferButton();
    }

    @Then("the transfer should be successful and the balance should be updated")
    public void transfer_should_be_successful() {
        assertTrue(fundTransferPage.isTransferSuccessful());
        assertTrue(fundTransferPage.isBalanceUpdated());
    }

    @Then("the user should see an error message indicating insufficient funds")
    public void insufficient_funds_error_message() {
        assertTrue(fundTransferPage.isInsufficientFundsErrorDisplayed());
    }
}
