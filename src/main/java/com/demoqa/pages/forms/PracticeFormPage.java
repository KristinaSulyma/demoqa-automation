package com.demoqa.pages.forms;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

/**
 * Page Object class representing the Practice Form page on demoqa.com.
 * Provides methods to interact with all form elements and perform operations like:
 * <ul>
 *   <li>Entering personal information</li>
 *   <li>Selecting gender, hobbies, and date of birth</li>
 *   <li>Uploading files</li>
 *   <li>Submitting the form</li>
 * </ul>
 */
public class PracticeFormPage extends BasePage {

    @FindBy(id = "firstName")
    WebElement firstNameInput;

    @FindBy(id = "lastName")
    WebElement lastNameInput;

    @FindBy(id = "userEmail")
    WebElement userEmailInput;

    @FindBy(css = "[for^='gender-radio']")
    List<WebElement> genderOptions;

    @FindBy(id = "userNumber")
    WebElement userNumberInput;

    @FindBy(id = "dateOfBirthInput")
    WebElement dateOfBirthInput;

    @FindBy(id = "subjectsInput")
    WebElement subjectsInput;

    @FindBy(css = "[for^='hobbies-checkbox']")
    List<WebElement> hobbiesOptions;

    @FindBy(id = "uploadPicture")
    WebElement uploadPictureButton;

    @FindBy(id = "currentAddress")
    WebElement currentAddressInput;

    @FindBy(id = "submit")
    WebElement submitButton;

    @FindBy(id = "example-modal-sizes-title-lg")
    WebElement modalHeader;

    @FindBy(id = "closeLargeModal")
    WebElement closeButton;

    /**
     * Constructor for PracticeFormPage.
     *
     * @param driver WebDriver instance
     */
    public PracticeFormPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Sets date of birth using direct input
     */
    public void setDateOfBirthSimple(String date) {
        WebElement element = waitForVisibility(dateOfBirthInput);
        element.clear();
        element.sendKeys(date);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Enters first name into the corresponding field.
     */
    public void enterFirstName(String firstName) {
        waitAndSendKeys(firstNameInput, firstName);
    }

    /**
     * Enters last name into the corresponding field.
     */
    public void enterLastName(String lastName) {
        waitAndSendKeys(lastNameInput, lastName);
    }

    /**
     * Enters email address into the corresponding field.
     */
    public void enterEmail(String email) {
        waitAndSendKeys(userEmailInput, email);
    }
    /**
     * Selects gender option.
     */
    public void selectGender(String gender) {
        genderOptions.stream()
                .filter(option -> option.getText().equalsIgnoreCase(gender))
                .findFirst()
                .ifPresent(this::jsClick);
    }

    /**
     * Enters phone number into the corresponding field.
     */
    public void enterPhoneNumber(String phone) {
        waitAndSendKeys(userNumberInput, phone);
    }

    /**
     * Enters subjects into the subjects field.
     */
    public void enterSubjects(String subjects) {
        WebElement element = waitForVisibility(subjectsInput);
        element.clear();
        element.sendKeys(subjects);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Selects hobbies from the available options.
     */
    public void selectHobbies(List<String> hobbies) {
        hobbiesOptions.stream()
                .filter(option -> hobbies.contains(option.getText()))
                .forEach(this::jsClick);
    }

    /**
     * Uploads a file using the file input.
     */
    public void uploadPicture(String filePath) {
        uploadPictureButton.sendKeys(filePath);
    }

    /**
     * Enters current address into the corresponding field.
     */
    public void enterAddress(String address) {
        waitAndSendKeys(currentAddressInput, address);
    }

    /**
     * Submits the form by clicking the submit button.
     */
    public void submitForm() {
        jsClick(submitButton);
    }


    /**
     * Checks if the submission modal is displayed.
     *
     * @return true if modal is displayed, false otherwise
     */
    public boolean isModalDisplayed() {
        try {
            waitForVisibility(modalHeader);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Gets the title text from the submission modal.
     *
     * @return Modal title text
     */
    public String getModalTitle() {
        return waitForVisibility(modalHeader).getText();
    }

    /**
     * Closes the submission modal.
     */
    public void closeModal() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("iframe[id^='google_ads_iframe']")));
            jsClick(closeButton);
        } catch (TimeoutException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
        }
    }

    /**
     * Clicks an element using JavaScript.
     *
     * @param element WebElement to click
     */
    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Waits for an element to become visible.
     *
     * @param element WebElement to wait for
     * @return Visible WebElement
     */
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for an element to be visible and sends keys to it.
     *
     * @param element WebElement to interact with
     * @param text    Text to send
     */
    private void waitAndSendKeys(WebElement element, String text) {
        WebElement visibleElement = waitForVisibility(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }
}