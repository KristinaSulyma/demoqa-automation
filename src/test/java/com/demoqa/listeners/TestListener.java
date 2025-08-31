package com.demoqa.listeners;

import com.demoqa.base.BaseTest;
import com.demoqa.utils.SeleniumUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

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
                String screenshotName = result.getMethod().getMethodName();
                byte[] allureScreenshot = saveScreenshotForAllure(driver);
                String filePath = SeleniumUtils.takeScreenshot(driver, screenshotName);

                if (allureScreenshot.length > 0 && filePath != null) {
                    LOGGER.info("Screenshot captured and saved successfully: {}", filePath);
                } else {
                    LOGGER.error("Failed to capture or save screenshot");
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
    private byte[] saveScreenshotForAllure(WebDriver driver) {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    /**
     * Logs test start information to console output.
     *
     * @param result The test result containing information about the starting test
     */
    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("Test started: {}", result.getName());
    }

    /**
     * Logs test success information to console output.
     *
     * @param result The test result containing information about the passed test
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test passed: {}", result.getName());    }

    /**
     * Logs test skip information to console output.
     *
     * @param result The test result containing information about the skipped test
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Test skipped: {}", result.getName());
    }
}