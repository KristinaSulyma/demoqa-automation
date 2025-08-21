package com.demoqa.pages.widgets;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the Slider widget page in the application.
 * This class provides methods to interact with the slider element and its value display.
 * Extends the BasePage class to inherit common page functionality.
 */
public class SliderPage extends BasePage {

    @FindBy(css = ".range-slider")
    private WebElement slider;

    @FindBy(id = "sliderValue")
    private WebElement sliderValue;

    /**
     * Constructs a new SliderPage instance.
     *
     * @param driver The WebDriver instance used for browser interactions
     */
    public SliderPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Sets the value of the slider to the specified integer value.
     * Uses JavaScript to directly update both the slider element and its value display.
     *
     * @param value The desired value to set the slider to
     */
    public void setSliderValue(int value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Sets the value directly for both the slider and input field
        // to ensure the value is properly updated
        js.executeScript("arguments[0].value = arguments[1]; arguments[2].value = arguments[1];", slider, value, sliderValue);
    }

    /**
     * Retrieves the current value displayed by the slider.
     *
     * @return The current value of the slider as a String
     */
    public String getSliderValue() {
        return sliderValue.getAttribute("value");
    }
}