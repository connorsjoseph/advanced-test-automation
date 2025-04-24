package ui.pages;

import core.base.BasePage;
import core.context.TestContext;
import org.openqa.selenium.By;

public class FundTransferPage extends BasePage {

    // Locators (update as per your actual HTML)
    private final By recipientAccountField = By.id("fromAccountId");
    private final By amountField = By.id("transfer-amount");
    private final By transferButton = By.id("transfer-btn");
    private final By successMessage = By.id("transfer-success-msg");
    private final By updatedBalance = By.id("updated-balance");
    private final By errorMessage = By.id("insufficient-funds-error");

    public FundTransferPage(TestContext context) {
        super(context);  // This sets driver from TestContext
    }

    public void enterRecipientAccountNumber(String accountNumber) {
        driver.findElement(recipientAccountField).clear();
        driver.findElement(recipientAccountField).sendKeys(accountNumber);
    }

    public void enterTransferAmount(String amount) {
        driver.findElement(amountField).clear();
        driver.findElement(amountField).sendKeys(amount);
    }

    public void clickTransferButton() {
        driver.findElement(transferButton).click();
    }

    public boolean isTransferSuccessful() {
        return driver.findElement(successMessage).isDisplayed();
    }

    public boolean isBalanceUpdated() {
        return driver.findElement(updatedBalance).isDisplayed();
    }

    public boolean isInsufficientFundsErrorDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }
}
