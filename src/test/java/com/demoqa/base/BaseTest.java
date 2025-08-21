package com.demoqa.base;

import com.demoqa.config.ConfigurationManager;
import com.demoqa.listeners.TestListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.*;

/**
 * Base test class that provides common test setup and teardown functionality.
 * Handles browser initialization, configuration, and cleanup for all test classes.
 * Includes TestNG listener for reporting and extended test capabilities.
 *
 * <p>Supported browsers:
 * <ul>
 *   <li>Chrome (default)</li>
 *   <li>Firefox</li>
 *   <li>Edge</li>
 * </ul>
 *
 * <p>Features:
 * <ul>
 *   <li>Automatic WebDriver management</li>
 *   <li>Browser configuration options</li>
 *   <li>Ad and notification blocking</li>
 *   <li>Page load and implicit wait timeouts</li>
 * </ul>
 */
@Listeners(TestListener.class)
public class BaseTest {
    /**
     * WebDriver instance (public for TestListener access)
     */
    public WebDriver driver;

    /**
     * Configuration manager for test properties
     */
    protected ConfigurationManager config;

    /**
     * WebDriverWait instance for explicit waits
     */
    protected WebDriverWait wait;

    /**
     * Sets up the test environment before each test method.
     * Initializes the WebDriver based on the specified browser parameter.
     * Configures browser options and sets timeouts.
     *
     * @param browser The browser to test against (chrome, firefox, edge).
     *                Defaults to "chrome" if not specified.
     * @throws IllegalArgumentException if an unsupported browser is specified
     */
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@org.testng.annotations.Optional("chrome") String browser) {
        config = new ConfigurationManager();

        if ("chrome".equalsIgnoreCase(browser)) {
            setupChrome();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            setupFirefox();
        } else if ("edge".equalsIgnoreCase(browser)) {
            setupEdge();
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeoutSeconds()));
        wait = new WebDriverWait(driver, Duration.ofSeconds(config.getImplicitWaitSeconds()));
    }

    /**
     * Configures and initializes Chrome browser with specific options.
     * Includes settings for:
     * - Disabling notifications and popups
     * - Blocking ads
     * - Removing automation flags
     */
    private void setupChrome() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Blocking ads and notifications
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--remote-allow-origins=*");

        // Disabling automation flags
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Ad-blocking preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.popups", 0);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.managed_default_content_settings.images", 2);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
    }

    /**
     * Configures and initializes Firefox browser with basic options.
     * Disables notifications by default.
     */
    private void setupFirefox() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-notifications");
        driver = new FirefoxDriver(options);
    }

    /**
     * Configures and initializes Edge browser with basic options.
     * Disables notifications and removes automation flags.
     */
    private void setupEdge() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        driver = new EdgeDriver(options);
    }

    /**
     * Provides access to the WebDriver instance.
     * Primarily used by TestListener for reporting purposes.
     *
     * @return The current WebDriver instance
     */
    public WebDriver getDriver() {
        return this.driver;
    }

    /**
     * Cleans up the test environment after each test method.
     * Closes all browser windows and terminates the WebDriver session.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}