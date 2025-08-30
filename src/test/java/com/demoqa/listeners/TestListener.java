package com.demoqa.listeners;

import com.demoqa.base.BaseTest;
import com.demoqa.utils.SeleniumUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A TestNG listener class that provides test monitoring capabilities and screenshot functionality.
 * <p>
 * This class implements {@link ITestListener} to capture test execution events and:
 * <ul>
 *   <li>Captures screenshots on test failures</li>
 *   <li>Attaches screenshots to Allure reports</li>
 *   <li>Saves screenshots locally</li>
 *   <li>Logs test execution status</li>
 * </ul>
 *
 * @see ITestListener
 * @see BaseTest
 * @see SeleniumUtils
 */
public class TestListener implements ITestListener {

    /**
     * Invoked when a test fails. Captures screenshot and attaches it to Allure report,
     * then saves it locally via {@link SeleniumUtils#takeScreenshot}.
     *
     * @param result The test result containing information about the failed test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        if (testClass instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testClass).getDriver();

            if (driver != null) {
                byte[] screenshotData = saveScreenshot(driver);
                String screenshotName = result.getMethod().getMethodName();


                saveScreenshotToFile(screenshotData, screenshotName);


                SeleniumUtils.takeScreenshot(driver, screenshotName);


                if (screenshotData.length > 0) {
                    System.out.println("Screenshot captured successfully: " + screenshotData.length + " bytes");
                } else {
                    System.out.println("Failed to capture screenshot");
                }
            }
        }
    }

    /**
     * Captures a screenshot of the current browser state and returns it as byte array.
     * <p>
     * The screenshot will be attached to the Allure report with the name "Failure Screenshot".
     *
     * @param driver The WebDriver instance used to capture the screenshot
     * @return Byte array representing the PNG screenshot, or empty array if capture fails
     */
    @Attachment(value = "Failure Screenshot", type = "image/png")
    private byte[] saveScreenshot(WebDriver driver) {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    /**
     * Saves screenshot bytes to a local file for additional backup.
     *
     * @param screenshotData The screenshot byte array
     * @param testName The name of the test for file naming
     */
    private void saveScreenshotToFile(byte[] screenshotData, String testName) {
        if (screenshotData.length == 0) {
            return;
        }

        try {
            Path screenshotsDir = Paths.get("target/screenshots");
            if (!Files.exists(screenshotsDir)) {
                Files.createDirectories(screenshotsDir);
            }


            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            Path filePath = screenshotsDir.resolve(fileName);


            Files.write(filePath, screenshotData);
            System.out.println("Screenshot saved to: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Failed to save screenshot to file: " + e.getMessage());
        }
    }

    /**
     * Logs test start information to console output.
     *
     * @param result The test result containing information about the starting test
     */
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getName());
    }

    /**
     * Logs test success information to console output.
     *
     * @param result The test result containing information about the passed test
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
    }

    /**
     * Logs test skip information to console output.
     *
     * @param result The test result containing information about the skipped test
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }
}