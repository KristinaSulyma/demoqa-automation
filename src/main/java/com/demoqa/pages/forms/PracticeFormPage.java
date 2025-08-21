package com.demoqa.pages.forms;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
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

    // Locators
    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "userEmail")
    private WebElement userEmailInput;

    @FindBy(css = "[for^='gender-radio']")
    private List<WebElement> genderOptions;

    @FindBy(id = "userNumber")
    private WebElement userNumberInput;

    @FindBy(id = "dateOfBirthInput")
    private WebElement dateOfBirthInput;

    @FindBy(id = "subjectsInput")
    private WebElement subjectsInput;

    @FindBy(css = "[for^='hobbies-checkbox']")
    private List<WebElement> hobbiesOptions;

    @FindBy(id = "uploadPicture")
    private WebElement uploadPictureButton;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressInput;

    @FindBy(id = "state")
    private WebElement stateDropdown;

    @FindBy(id = "city")
    private WebElement cityDropdown;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "example-modal-sizes-title-lg")
    private WebElement modalHeader;

    @FindBy(id = "closeLargeModal")
    private WebElement closeButton;

    @FindBy(className = "react-datepicker__month-select")
    private WebElement monthSelect;

    @FindBy(className = "react-datepicker__year-select")
    private WebElement yearSelect;

    /**
     * Constructor for PracticeFormPage.
     * @param driver WebDriver instance
     */
    public PracticeFormPage(WebDriver driver) {
        super(driver);
    }

    // Main methods
    /**
     * Enters first name into the corresponding field.
     * @param firstName First name to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterFirstName(String firstName) {
        waitAndSendKeys(firstNameInput, firstName);
        return this;
    }

    /**
     * Enters last name into the corresponding field.
     * @param lastName Last name to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterLastName(String lastName) {
        waitAndSendKeys(lastNameInput, lastName);
        return this;
    }

    /**
     * Enters email address into the corresponding field.
     * @param email Email to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterEmail(String email) {
        waitAndSendKeys(userEmailInput, email);
        return this;
    }

    /**
     * Selects gender option.
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
     * @param phone Phone number to enter (10 digits)
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterPhoneNumber(String phone) {
        waitAndSendKeys(userNumberInput, phone);
        return this;
    }

    /**
     * Sets date of birth using the date picker.
     * @param day Day of birth (e.g., "15")
     * @param month Month of birth (e.g., "January")
     * @param year Year of birth (e.g., "1990")
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage setDateOfBirth(String day, String month, String year) {
        jsClick(dateOfBirthInput);
        selectFromDropdown(monthSelect, month);
        selectFromDropdown(yearSelect, year);
        selectDayInCalendar(day);
        return this;
    }

    /**
     * Enters subjects into the subjects field.
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
     * @param filePath Full path to the file to upload
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage uploadPicture(String filePath) {
        uploadPictureButton.sendKeys(filePath);
        return this;
    }

    /**
     * Enters current address into the corresponding field.
     * @param address Address to enter
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage enterAddress(String address) {
        waitAndSendKeys(currentAddressInput, address);
        return this;
    }

    /**
     * Selects state from the dropdown.
     * @param state State to select
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage selectState(String state) {
        selectFromDropdown(stateDropdown, state);
        return this;
    }

    /**
     * Selects city from the dropdown.
     * @param city City to select
     * @return Current instance of PracticeFormPage for method chaining
     */
    public PracticeFormPage selectCity(String city) {
        selectFromDropdown(cityDropdown, city);
        return this;
    }

    /**
     * Submits the form by clicking the submit button.
     */
    public void submitForm() {
        jsClick(submitButton);
    }

    // Verification methods
    /**
     * Checks if the submission modal is displayed.
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
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", closeButton);
        }
    }

    // Helper methods
    /**
     * Clicks an element using JavaScript.
     * @param element WebElement to click
     */
    protected void jsClick(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Waits for an element to become visible.
     * @param element WebElement to wait for
     * @return Visible WebElement
     */
    protected WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for an element to be visible and sends keys to it.
     * @param element WebElement to interact with
     * @param text Text to send
     */
    private void waitAndSendKeys(WebElement element, String text) {
        WebElement visibleElement = waitForVisibility(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }

    /**
     * Selects an option from a dropdown.
     * @param dropdown Dropdown WebElement
     * @param value Value to select
     */
    private void selectFromDropdown(WebElement dropdown, String value) {
        jsClick(dropdown);
        WebElement option = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//div[contains(text(),'%s')]", value))));
        jsClick(option);
    }

    /**
     * Selects a day in the date picker calendar.
     * @param day Day to select
     */
    private void selectDayInCalendar(String day) {
        WebElement dayElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath(String.format("//div[contains(@class,'react-datepicker__day') and text()='%s']", day))));
        jsClick(dayElement);
    }

    // Legacy methods (for backward compatibility)
    /**
     * @deprecated Use {@link #enterFirstName(String)} instead
     */
    @Deprecated
    public void fillFirstName(String firstName) {
        enterFirstName(firstName);
    }

    /**
     * @deprecated Use {@link #enterLastName(String)} instead
     */
    @Deprecated
    public void fillLastName(String lastName) {
        enterLastName(lastName);
    }

    /**
     * @deprecated Use {@link #enterEmail(String)} instead
     */
    @Deprecated
    public void fillEmail(String email) {
        enterEmail(email);
    }

    /**
     * @deprecated Use {@link #selectGender(String)} instead
     */
    @Deprecated
    public void selectGenderOld(String gender) {
        selectGender(gender);
    }

    /**
     * @deprecated Use {@link #enterPhoneNumber(String)} instead
     */
    @Deprecated
    public void fillUserNumber(String number) {
        enterPhoneNumber(number);
    }

    /**
     * @deprecated Use {@link #setDateOfBirth(String, String, String)} instead
     */
    @Deprecated
    public void fillDateOfBirth(String date) {
        dateOfBirthInput.sendKeys(date);
    }

    /**
     * @deprecated Use {@link #selectHobbies(List)} instead
     */
    @Deprecated
    public void selectHobby(String hobby) {
        selectHobbies(List.of(hobby));
    }

    /**
     * @deprecated Use {@link #enterAddress(String)} instead
     */
    @Deprecated
    public void fillCurrentAddress(String address) {
        enterAddress(address);
    }

    /**
     * @deprecated Use {@link #uploadPicture(String)} instead
     */
    @Deprecated
    public void uploadPictureOld(String filePath) {
        uploadPicture(filePath);
    }

    /**
     * @deprecated Use {@link #enterSubjects(String)} instead
     */
    @Deprecated
    public void fillSubjects(String subjects) {
        enterSubjects(subjects);
    }

    /**
     * @deprecated Use {@link #selectState(String)} instead
     */
    @Deprecated
    public void selectStateOld(String state) {
        selectState(state);
    }

    /**
     * @deprecated Use {@link #selectCity(String)} instead
     */
    @Deprecated
    public void selectCityOld(String city) {
        selectCity(city);
    }
}