package com.demoqa.pages.widgets;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object class for interacting with the Date Picker widget on demoqa.com.
 * Provides methods to select and retrieve date/time values using JavaScript execution.
 */
public class DatePickerPage extends BasePage {

    @FindBy(id = "dateAndTimePickerInput")
    private WebElement dateAndTimePickerInput;

    /**
     * Constructs a new DatePickerPage instance.
     * @param driver The WebDriver instance used for page interactions
     */
    public DatePickerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Sets the date and time value using JavaScript execution.
     * This bypasses the actual date picker widget and directly sets the input value.
     * @param dateTime The date and time string to set in the format expected by the widget
     *                (e.g., "MM/DD/YYYY HH:MM AM/PM")
     */
    public void selectDateAndTime(String dateTime) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1]", dateAndTimePickerInput, dateTime);
    }

    /**
     * Gets the currently selected date and time value from the input field.
     * @return The string representation of the selected date and time
     */
    public String getDatePickerValue() {
        return dateAndTimePickerInput.getAttribute("value");
    }
}