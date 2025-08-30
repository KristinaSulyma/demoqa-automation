package com.demoqa.smoke;

import com.demoqa.base.BaseTest;
import com.demoqa.config.ConfigurationManager;
import com.demoqa.pages.elements.TextBoxPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Smoke test suite for basic verification of DemoQA website functionality.
 * <p>
 * This class extends {@link BaseTest} and contains fundamental tests to verify:
 * <ul>
 *   <li>Basic website availability</li>
 *   <li>Core page loading functionality</li>
 * </ul>
 *
 * <p>Tests are designed to quickly verify the most critical paths of the application.
 *
 * @see BaseTest
 * @see TextBoxPage
 * @see ConfigurationManager
 */
public class SmokeTests extends BaseTest {

    private TextBoxPage textBoxPage;
    private WebDriverWait wait;

    /**
     * Initializes page objects before each test method execution.
     * <p>
     * Prepares the {@link TextBoxPage} instance for use in test methods.
     */
    @BeforeMethod
    public void setupPages() {
        textBoxPage = new TextBoxPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Verifies that the DemoQA home page loads successfully.
     * <p>
     * Tests basic website availability by:
     * <ol>
     *   <li>Navigating to the base URL</li>
     *   <li>Validating the page title matches expected value</li>
     * </ol>
     *
     * @see ConfigurationManager#getBaseUrl()
     */
    @Test
    public void testHomePageLoadsSuccessfully() {
        driver.get(new ConfigurationManager().getBaseUrl());
        wait.until(ExpectedConditions.titleIs("DEMOQA"));
        Assert.assertEquals(driver.getTitle(), "DEMOQA",
                "Home page title should be 'DEMOQA'.");
    }

    /**
     * Verifies that the Text Box page loads successfully.
     * <p>
     * Tests page navigation functionality by:
     * <ol>
     *   <li>Navigating to the Text Box page URL</li>
     *   <li>Validating the current URL matches expected value</li>
     * </ol>
     *
     * @see ConfigurationManager#getBaseUrl()
     */
    @Test
    public void testTextBoxPageLoadsSuccessfully() {
        String expectedUrl = new ConfigurationManager().getBaseUrl() + "/text-box";
        driver.get(expectedUrl);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl,
                "Text Box page URL should be correct.");
    }
}