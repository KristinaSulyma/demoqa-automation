package com.demoqa.pages.elements;

import com.demoqa.pages.BasePage;
import com.demoqa.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object class for interacting with the Text Box form on demoqa.com.
 * Provides methods to fill and submit text input fields and verify results.
 */
public class TextBoxPage extends BasePage {

    @FindBy(id = "userName")
    private WebElement fullNameInput;

    @FindBy(id = "userEmail")
    private WebElement emailInput;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressInput;

    @FindBy(id = "permanentAddress")
    private WebElement permanentAddressInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "output")
    private WebElement outputBox;

    /**
     * Constructs a new TextBoxPage instance.
     * @param driver The WebDriver instance used for page interactions
     */
    public TextBoxPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fills all text input fields in the form.
     * @param fullName The full name to enter
     * @param email The email address to enter
     * @param currentAddress The current address to enter
     * @param permanentAddress The permanent address to enter
     */
    public void fillForm(String fullName, String email, String currentAddress, String permanentAddress) {
        fullNameInput.sendKeys(fullName);
        emailInput.sendKeys(email);
        currentAddressInput.sendKeys(currentAddress);
        permanentAddressInput.sendKeys(permanentAddress);
    }

    /**
     * Submits the form by clicking the submit button using JavaScript.
     * Waits for the submit button to be visible before clicking.
     */
    public void submitForm() {
        wait.until(ExpectedConditions.visibilityOf(submitButton));
        SeleniumUtils.clickWithJS(driver, submitButton);
    }

    /**
     * Checks if the output box with submitted data is displayed.
     * @return true if the output box is visible, false otherwise
     */
    public boolean isOutputDisplayed() {
        return outputBox.isDisplayed();
    }
}