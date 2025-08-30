package com.demoqa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all page objects in the application.
 * Provides common functionality and utilities for web page interactions.
 * All specific page classes should extend this base class.
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Initializes a new instance of the BasePage.
     * Performs common setup operations including:
     * - Initializing WebDriver and WebDriverWait
     * - Initializing PageFactory elements
     * - Waiting for page to load
     * - Attempting to remove ads
     *
     * @param driver The WebDriver instance for browser interactions
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
        waitForPageToLoad();
        removeAds();
    }

    /**
     * Waits for the page to completely load by checking document.readyState.
     * Silently catches and logs WebDriverException if the wait is interrupted.
     */
    protected void waitForPageToLoad() {
        try {
            wait.until(d -> "complete".equals(((JavascriptExecutor) driver)
                    .executeScript("return document.readyState")));
        } catch (WebDriverException e) {
            System.out.println("Page load wait interrupted: " + e.getMessage());
        }
    }

    /**
     * Attempts to remove common advertisement elements from the page.
     * Looks for elements matching common ad selectors (iframes, ins tags, elements with 'ad' in id/class)
     * and removes them using JavaScript.
     * Silently catches and logs any exceptions during the process.
     */
    protected void removeAds() {
        try {
            List<WebElement> ads = driver.findElements(By.cssSelector(
                    "iframe, ins, .ad, [id*='ad'], [class*='ad'], [id*='Ad'], [class*='Ad']"));

            if (!ads.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].forEach(ad => ad.remove())", ads);
            }
        } catch (Exception e) {
            System.out.println("Failed to remove ads: " + e.getMessage());
        }
    }

    /**
     * Clicks an element using JavaScript, with proper waiting and scrolling.
     * Handles StaleElementReferenceException by refreshing the element reference.
     *
     * @param element The WebElement to be clicked
     */
    protected void clickWithJS(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            scrollIntoView(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (StaleElementReferenceException e) {
            WebElement refreshedElement = wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(element)));
            scrollIntoView(refreshedElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", refreshedElement);
        }
    }

    /**
     * Scrolls the specified element into view with smooth behavior.
     *
     * @param element The WebElement to scroll into view
     */
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Waits for the specified element to become visible.
     *
     * @param element The WebElement to wait for
     * @return The visible WebElement
     */
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}