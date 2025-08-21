package com.demoqa.pages.widgets;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object class for interacting with the Progress Bar widget on demoqa.com.
 * Provides methods to control and monitor the progress bar's behavior.
 */
public class ProgressBarPage extends BasePage {

    @FindBy(id = "startStopButton")
    private WebElement startStopButton;

    @FindBy(css = ".progress-bar")
    private WebElement progressBar;

    /**
     * Constructs a new ProgressBarPage instance.
     * @param driver The WebDriver instance used for page interactions
     */
    public ProgressBarPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Toggles the progress bar (starts or stops it) using JavaScript click.
     * Performs the following actions:
     * <ol>
     *   <li>Removes any interfering ads</li>
     *   <li>Clicks the start/stop button using JavaScript</li>
     *   <li>Waits until the progress begins (value > 0)</li>
     * </ol>
     */
    public void startStopProgressBar() {
        removeAds();
        clickWithJS(startStopButton);

        wait.until(d -> {
            String value = getProgressBarValue();
            return value != null && !value.isEmpty() && !value.equals("0");
        });
    }

    /**
     * Waits for the progress bar to reach 100% completion.
     * Uses explicit wait to poll the progress value until it reaches "100".
     */
    public void waitForCompletion() {
        wait.until(d -> "100".equals(getProgressBarValue()));
    }

    /**
     * Gets the current progress value from the progress bar.
     * @return The current progress percentage as a String (0-100),
     *         or null if the value cannot be retrieved
     */
    public String getProgressBarValue() {
        try {
            return progressBar.getAttribute("aria-valuenow");
        } catch (Exception e) {
            return null;
        }
    }
}