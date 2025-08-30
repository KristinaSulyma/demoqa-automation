package com.demoqa.pages.alertsframes;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Class for Alerts Page.
 * Provides methods to interact with different types of JavaScript alerts:
 * - Simple alerts
 * - Timed alerts
 * - Confirmation dialogs
 * - Prompt dialogs
 *
 * <p>All methods include proper waits to ensure stable test execution.
 */
public class AlertsPage extends BasePage {

    @FindBy(id = "alertButton")
    private WebElement alertButton;

    @FindBy(id = "timerAlertButton")
    private WebElement timerAlertButton;

    @FindBy(id = "confirmButton")
    private WebElement confirmButton;

    @FindBy(id = "promtButton")
    private WebElement promptButton;

    @FindBy(id = "confirmResult")
    private WebElement confirmResultText;

    @FindBy(id = "promptResult")
    private WebElement promptResultText;

    /**
     * Constructor for AlertsPage.
     * @param driver WebDriver instance
     */
    public AlertsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the button to trigger a simple alert.
     * Waits for the button to be clickable before clicking.
     */
    public void clickAlertButton() {
        waitForElementToBeClickable(alertButton).click();
        waitForAlert();
    }

    /**
     * Clicks the button to trigger a confirmation dialog.
     * Waits for the alert to be present after clicking.
     */
    public void clickConfirmButton() {
        waitForElementToBeClickable(confirmButton).click();
        waitForAlert();
    }

    /**
     * Clicks the button to trigger a prompt dialog.
     * Handles potential ad overlays before clicking.
     * Uses a combination of regular click and JavaScript click as fallback.
     */
    public void clickPromptButton() {
        handleAdIfPresent();

        WebElement button = waitForElementToBeClickable(promptButton);

        try {
            button.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", button);
        }

        waitForAlert();
    }

    /**
     * Gets the result text after interacting with a confirmation dialog.
     * @return Result text showing the user's choice
     */
    public String getConfirmResultText() {
        return wait.until(ExpectedConditions.visibilityOf(confirmResultText)).getText();
    }

    /**
     * Gets the result text after interacting with a prompt dialog.
     * @return Result text showing the entered text
     */
    public String getPromptResultText() {
        return wait.until(ExpectedConditions.visibilityOf(promptResultText)).getText();
    }

    /**
     * Attempts to handle advertisement iframes that might block interaction with page elements.
     * Switches to Ad iframe if present, tries to close it, then returns to main content.
     */
    private void handleAdIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

            shortWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    By.cssSelector("iframe[title*='ad'], iframe[aria-label*='ad'], iframe[title*='Ad.Plus']")));

            List<WebElement> closeButtons = driver.findElements(
                    By.cssSelector("[aria-label*='Close'], [title*='Close'], .close-button, .ads-close-button"));

            if (!closeButtons.isEmpty()) {
                closeButtons.getFirst().click();
                System.out.println("Ad closed successfully.");
            }

        } catch (Exception e) {
            System.out.println("No ad found or could not close ad: " + e.getMessage());
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    /**
     * Waits for alert to be present.
     * @throws org.openqa.selenium.TimeoutException if alert doesn't appear within wait time
     */
    private void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Waits for element to be clickable.
     * @param element WebElement to wait for
     * @return Clickable WebElement
     * @throws org.openqa.selenium.TimeoutException if element doesn't become clickable
     */
    private WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}