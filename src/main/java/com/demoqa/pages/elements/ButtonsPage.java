package com.demoqa.pages.elements;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object class for handling different button interactions on demoqa.com.
 * Provides methods to perform and verify various click actions:
 * <ul>
 *   <li>Double click</li>
 *   <li>Right click</li>
 *   <li>Regular click</li>
 * </ul>
 */
public class ButtonsPage extends BasePage {

    @FindBy(id = "doubleClickBtn")
    private WebElement doubleClickButton;

    @FindBy(id = "rightClickBtn")
    private WebElement rightClickButton;

    @FindBy(xpath = "//button[text()='Click Me']")
    private WebElement clickMeButton;

    @FindBy(id = "doubleClickMessage")
    private WebElement doubleClickMessage;

    @FindBy(id = "rightClickMessage")
    private WebElement rightClickMessage;

    @FindBy(id = "dynamicClickMessage")
    private WebElement dynamicClickMessage;

    /**
     * Constructs a new ButtonsPage instance.
     * @param driver The WebDriver instance used for interactions
     */
    public ButtonsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Performs a double click action on the designated button.
     * Waits for the button to be clickable before performing the action.
     */
    public void doubleClickMe() {
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.elementToBeClickable(doubleClickButton));
        actions.doubleClick(doubleClickButton).perform();
    }

    /**
     * Performs a right click (context click) action on the designated button.
     * Waits for the button to be clickable before performing the action.
     */
    public void rightClickMe() {
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.elementToBeClickable(rightClickButton));
        actions.contextClick(rightClickButton).perform();
    }

    /**
     * Performs a regular click action on the 'Click Me' button.
     * Waits for the button to be clickable before performing the action.
     */
    public void clickMe() {
        wait.until(ExpectedConditions.elementToBeClickable(clickMeButton));
        clickMeButton.click();
    }

    /**
     * Retrieves the success message displayed after double clicking.
     * @return Text of the double click confirmation message
     */
    public String getDoubleClickMessage() {
        return doubleClickMessage.getText();
    }

    /**
     * Retrieves the success message displayed after right clicking.
     * @return Text of the right click confirmation message
     */
    public String getRightClickMessage() {
        return rightClickMessage.getText();
    }

    /**
     * Retrieves the success message displayed after regular clicking.
     * @return Text of the regular click confirmation message
     */
    public String getDynamicClickMessage() {
        return dynamicClickMessage.getText();
    }
}