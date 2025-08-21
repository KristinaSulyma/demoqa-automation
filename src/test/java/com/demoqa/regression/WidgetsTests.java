package com.demoqa.regression;

import com.demoqa.pages.widgets.DatePickerPage;
import com.demoqa.pages.widgets.ProgressBarPage;
import com.demoqa.pages.widgets.SliderPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Regression test class for testing various widgets on the DemoQA website.
 * <p>
 * This class contains test cases for:
 * <ul>
 *   <li>Slider widget functionality</li>
 *   <li>Progress bar completion</li>
 *   <li>Date picker selection</li>
 * </ul>
 *
 * <p>Uses TestNG framework for test execution and WebDriver for browser automation.
 *
 * @see DatePickerPage
 * @see ProgressBarPage
 * @see SliderPage
 */
public class WidgetsTests {

    private WebDriver driver;
    private DatePickerPage datePickerPage;
    private ProgressBarPage progressBarPage;
    private SliderPage sliderPage;

    /**
     * Sets up the test environment before each test method execution.
     * <p>
     * Initializes the ChromeDriver, maximizes the browser window,
     * and initializes all page objects needed for the tests.
     */
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        datePickerPage = new DatePickerPage(driver);
        progressBarPage = new ProgressBarPage(driver);
        sliderPage = new SliderPage(driver);
    }

    /**
     * Tests the slider widget functionality by setting a specific value
     * and verifying the slider moves to the correct position.
     *
     * @see SliderPage#setSliderValue(int)
     * @see SliderPage#getSliderValue()
     */
    @Test
    public void testSliderMovement() {
        System.out.println("Running test: testSliderMovement");
        driver.get("https://demoqa.com/slider");
        int targetValue = 75;
        sliderPage.setSliderValue(targetValue);
        Assert.assertEquals(sliderPage.getSliderValue(), String.valueOf(targetValue),
                "Slider value should be updated to " + targetValue);
    }

    /**
     * Tests the progress bar widget by starting it and waiting for completion,
     * then verifying it reaches 100%.
     *
     * @see ProgressBarPage#startStopProgressBar()
     * @see ProgressBarPage#waitForCompletion()
     * @see ProgressBarPage#getProgressBarValue()
     */
    @Test
    public void testProgressBarCompletion() {
        System.out.println("Running test: testProgressBarCompletion");
        driver.get("https://demoqa.com/progress-bar");
        progressBarPage.startStopProgressBar();
        progressBarPage.waitForCompletion();
        Assert.assertEquals(progressBarPage.getProgressBarValue(), "100",
                "Progress bar should reach 100%");
    }

    /**
     * Tests the date picker widget by selecting a specific date and time
     * and verifying the selected value matches the expected input.
     *
     * @see DatePickerPage#selectDateAndTime(String)
     * @see DatePickerPage#getDatePickerValue()
     */
    @Test
    public void testDatePicker() {
        System.out.println("Running test: testDatePicker");
        driver.get("https://demoqa.com/date-picker");
        String expectedDate = "02/14/2024 10:30 AM";
        datePickerPage.selectDateAndTime(expectedDate);
        String actualDate = datePickerPage.getDatePickerValue();
        Assert.assertEquals(actualDate, expectedDate,
                "Date and time should be updated correctly");
    }

    /**
     * Cleans up the test environment after each test method execution.
     * <p>
     * Closes the browser instance if it exists.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}