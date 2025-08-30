package com.demoqa.functional;

import com.demoqa.base.BaseTest;
import com.demoqa.config.ConfigurationManager;
import com.demoqa.pages.alertsframes.AlertsPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

/**
 * Functional test class for verifying JavaScript alerts functionality.
 * Contains test cases for different types of browser alerts:
 * - Simple alert (testSimpleAlert)
 * - Confirmation alert (testConfirmAlert)
 * - Prompt alert (testPromptAlert)
 *
 * <p>All tests follow the same pattern:
 * <ol>
 *   <li>Navigate to alerts page</li>
 *   <li>Trigger specific alert type</li>
 *   <li>Verify alert behavior and content</li>
 * </ol>
 *
 * <p>Extends BaseTest to inherit common test setup and browser management.
 */
public class AlertsFramesTests extends BaseTest {

    /**
     * Page object for interacting with alerts page elements
     */
    private AlertsPage alertsPage;
    private WebDriverWait wait;

    /**
     * Initializes page objects before each test method execution.
     * Creates new instance of AlertsPage bound to the current WebDriver.
     */
    @BeforeMethod
    public void setupPages() {
        alertsPage = new AlertsPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Tests basic JavaScript alert functionality.
     * Verifies:
     * <ul>
     *   <li>Alert appears when button is clicked</li>
     *   <li>Alert contains expected text message</li>
     *   <li>Alert can be successfully accepted</li>
     * </ul>
     */
    @Test
    public void testSimpleAlert() {
        driver.get(new ConfigurationManager().getBaseUrl() + "/alerts");

        alertsPage.clickAlertButton();

        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertEquals(alert.getText(), "You clicked a button",
                    "Alert text mismatch - expected standard alert message");
            alert.accept();
        } catch (TimeoutException e) {
            Assert.fail("Alert did not appear within 15 seconds");
        }
    }

    /**
     * Tests confirmation alert dialog functionality.
     * Verifies:
     * <ul>
     *   <li>Confirmation dialog appears when button is clicked</li>
     *   <li>Dialog can be accepted</li>
     *   <li>Page displays correct result after acceptance</li>
     * </ul>
     */
    @Test
    public void testConfirmAlert() {
        driver.get(new ConfigurationManager().getBaseUrl() + "/alerts");

        alertsPage.clickConfirmButton();

        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            Assert.assertTrue(alertsPage.getConfirmResultText().contains("Ok"),
                    "Confirmation result should acknowledge 'Ok' selection");
        } catch (TimeoutException e) {
            Assert.fail("Confirmation alert did not appear within 15 seconds");
        }
    }

    /**
     * Tests prompt alert functionality with text input.
     * Verifies:
     * <ul>
     *   <li>Prompt dialog appears when button is clicked</li>
     *   <li>Text can be successfully entered into prompt</li>
     *   <li>Page displays the entered text after submission</li>
     * </ul>
     */
    @Test
    public void testPromptAlert() {
        final String testText = "Hello World";
        driver.get(new ConfigurationManager().getBaseUrl() + "/alerts");

        alertsPage.clickPromptButton();

        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(testText);
            alert.accept();
            Assert.assertTrue(alertsPage.getPromptResultText().contains(testText),
                    "Prompt result should echo the entered text");
        } catch (TimeoutException e) {
            Assert.fail("Prompt alert did not appear within 15 seconds");
        }
    }
}