package com.demoqa.pages.elements;

import com.demoqa.pages.BasePage;
import com.demoqa.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

/**
 * Page Object class for interacting with the Radio Button page on demoqa.com.
 * Provides methods to select and verify radio button states and results.
 */
public class RadioButtonPage extends BasePage {

    @FindBy(id = "yesRadio")
    private WebElement yesRadioButton;

    @FindBy(id = "impressiveRadio")
    private WebElement impressiveRadioButton;

    @FindBy(id = "noRadio")
    private WebElement noRadioButton;

    @FindBy(css = "p.mt-3")
    private WebElement resultText;

    /**
     * Constructs a new RadioButtonPage instance.
     * @param driver The WebDriver instance used for page interactions
     */
    public RadioButtonPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects the "Yes" radio button by clicking its associated label using JavaScript.
     * Waits for the label to be clickable before attempting selection.
     */
    public void clickYesRadioButton() {
        WebElement labelElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("label[for='yesRadio']")));
        SeleniumUtils.clickWithJS(driver, labelElement);
    }

    /**
     * Selects the "Impressive" radio button by clicking its associated label using JavaScript.
     * Waits for the label to be clickable before attempting selection.
     */
    public void clickImpressiveRadioButton() {
        WebElement labelElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("label[for='impressiveRadio']")));
        SeleniumUtils.clickWithJS(driver, labelElement);
    }

    /**
     * Checks if the "No" radio button is enabled for selection.
     * @return true if the radio button is enabled, false otherwise
     */
    public boolean isNoRadioButtonEnabled() {
        return noRadioButton.isEnabled();
    }

    /**
     * Gets the result text displayed after selecting a radio button.
     * Waits for the result text to become visible before retrieving.
     * @return The text result showing the selected radio button
     */
    public String getResultText() {
        wait.until(ExpectedConditions.visibilityOf(resultText));
        return resultText.getText();
    }
}