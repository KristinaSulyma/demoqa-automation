package com.demoqa.smoke;

import com.demoqa.base.BaseTest;
import com.demoqa.config.ConfigurationManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class SmokeTests extends BaseTest {

    /**
     * Tests successful loading of the home page.
     * Verifies that the page title matches the expected value "DEMOQA".
     * Uses WebDriverWait to wait for the page to load completely.
     */
    @Test
    public void testHomePageLoadsSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(new ConfigurationManager().getBaseUrl());
        wait.until(ExpectedConditions.titleIs("DEMOQA"));
        Assert.assertEquals(driver.getTitle(), "DEMOQA",
                "Home page title should be 'DEMOQA'.");
    }

    /**
     * Tests successful loading of the Text Box page.
     * Verifies that the page URL matches the expected path "/text-box".
     * Uses WebDriverWait to wait for navigation to the correct URL.
     */
    @Test
    public void testTextBoxPageLoadsSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String expectedUrl = new ConfigurationManager().getBaseUrl() + "/text-box";
        driver.get(expectedUrl);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl,
                "Text Box page URL should be correct.");
    }
}