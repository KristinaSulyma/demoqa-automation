package com.demoqa.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     * Takes a screenshot and saves it to the target/screenshots directory.
     * Creates the directory if it doesn't exist.
     *
     * @param driver The WebDriver instance capable of taking screenshots
     * @param screenshotName The base name for the screenshot file (without extension)
     * @return The full path to the saved screenshot, or null if saving failed
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            Path screenshotsDir = Paths.get("target/screenshots");
            if (!Files.exists(screenshotsDir)) {
                Files.createDirectories(screenshotsDir);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = screenshotName + "_" + timestamp + ".png";
            File destinationFile = new File(screenshotsDir.toFile(), fileName);

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(screenshotFile, destinationFile);

            String absolutePath = destinationFile.getAbsolutePath();
            System.out.println("Screenshot saved: " + absolutePath);

            return absolutePath;

        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while taking screenshot: " + e.getMessage());
            return null;
        }
    }
}