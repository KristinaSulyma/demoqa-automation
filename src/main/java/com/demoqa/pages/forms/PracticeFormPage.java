package com.demoqa.pages.forms;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
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
     * Sets date of birth using direct input (alternative method)
     *
     * @param date Date in format "MM/DD/YYYY" or other supported format
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage setDateOfBirthSimple(String date) {
        // Очищаємо поле і вводимо дату напряму
        WebElement element = waitForVisibility(dateOfBirthInput);
        element.clear();
        element.sendKeys(date);
        element.sendKeys(Keys.ENTER);
        return this;
    }

    /**
     * Enters first name into the corresponding field.
     *
     * @param firstName First name to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterFirstName(String firstName) {
        waitAndSendKeys(firstNameInput, firstName);
        return this;
    }

    /**
     * Enters last name into the corresponding field.
     *
     * @param lastName Last name to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterLastName(String lastName) {
        waitAndSendKeys(lastNameInput, lastName);
        return this;
    }

    /**
     * Enters email address into the corresponding field.
     *
     * @param email Email to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterEmail(String email) {
        waitAndSendKeys(userEmailInput, email);
        return this;
    }

    /**
     * Selects gender option.
     *
     * @param gender Gender to select ("Male", "Female", or "Other")
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage selectGender(String gender) {
        genderOptions.stream()
                .filter(option -> option.getText().equalsIgnoreCase(gender))
                .findFirst()
                .ifPresent(this::jsClick);
        return this;
    }

    /**
     * Enters phone number into the corresponding field.
     *
     * @param phone Phone number to enter (10 digits)
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterPhoneNumber(String phone) {
        waitAndSendKeys(userNumberInput, phone);
        return this;
    }

    /**
     * Enters subjects into the subjects field.
     *
     * @param subjects Subjects to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterSubjects(String subjects) {
        WebElement element = waitForVisibility(subjectsInput);
        element.clear();
        element.sendKeys(subjects);
        element.sendKeys(Keys.ENTER);
        return this;
    }

    /**
     * Selects hobbies from the available options.
     *
     * @param hobbies List of hobbies to select
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage selectHobbies(List<String> hobbies) {
        hobbiesOptions.stream()
                .filter(option -> hobbies.contains(option.getText()))
                .forEach(this::jsClick);
        return this;
    }

    /**
     * Uploads a file using the file input.
     *
     * @param filePath Full path to the file to upload
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage uploadPicture(String filePath) {
        uploadPictureButton.sendKeys(filePath);
        return this;
    }

    /**
     * Enters current address into the corresponding field.
     *
     * @param address Address to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterAddress(String address) {
        waitAndSendKeys(currentAddressInput, address);
        return this;
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
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                            By.cssSelector("iframe[id^='google_ads_iframe']")));
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
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOf(element));
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