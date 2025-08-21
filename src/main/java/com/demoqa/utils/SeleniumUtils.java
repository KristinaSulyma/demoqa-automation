package com.demoqa.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Utility class providing common Selenium WebDriver helper methods.
 * Contains static methods for JavaScript interactions, waiting strategies, and screenshot functionality.
 */
public class SeleniumUtils {

    /**
     * Clicks on a WebElement using JavaScript executor.
     * Useful when normal click operations don't work due to visibility or other issues.
     *
     * @param driver The WebDriver instance
     * @param element The WebElement to be clicked
     */
    public static void clickWithJS(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Enters text into a WebElement using JavaScript executor.
     * Bypasses normal sendKeys() when direct text input is problematic.
     *
     * @param driver The WebDriver instance
     * @param element The WebElement to receive text
     * @param text The text to be entered
     */
    public static void enterTextWithJS(WebDriver driver, WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + text + "';", element);
    }

    /**
     * Scrolls the page to make the specified element visible in the viewport.
     *
     * @param driver The WebDriver instance
     * @param element The WebElement to scroll to
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Takes a screenshot and saves it to the target/screenshots directory.
     * Creates the directory if it doesn't exist.
     *
     * @param driver The WebDriver instance capable of taking screenshots
     * @param screenshotName The base name for the screenshot file (without extension)
     * @return The full path to the saved screenshot, or null if saving failed
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String targetPath = "target/screenshots/" + screenshotName + ".png";

        try {
            Path path = Paths.get("target/screenshots/");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            FileHandler.copy(screenshotFile, new File(targetPath));
            return targetPath;
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Waits for an element to become clickable within the specified timeout.
     *
     * @param driver The WebDriver instance
     * @param element The WebElement to wait for
     * @param timeout The maximum duration to wait
     */
    public static void waitForElementToBeClickable(WebDriver driver, WebElement element, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for an element to become visible within the specified timeout.
     *
     * @param driver The WebDriver instance
     * @param element The WebElement to wait for
     * @param timeout The maximum duration to wait
     */
    public static void waitForElementToBeVisible(WebDriver driver, WebElement element, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}