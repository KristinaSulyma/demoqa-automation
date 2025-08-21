package com.demoqa.pages.alertsframes;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object class for handling iframes on demoqa.com.
 * Provides methods to switch between frames and interact with their content.
 */
public class FramesPage extends BasePage {

    @FindBy(id = "frame1")
    private WebElement frame1;

    @FindBy(id = "frame2")
    private WebElement frame2;

    /**
     * Constructs a new FramesPage instance.
     * @param driver The WebDriver instance used for interactions
     */
    public FramesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Switches the driver's focus to the first iframe (frame1).
     */
    public void switchToFrame1() {
        driver.switchTo().frame(frame1);
    }

    /**
     * Switches the driver's focus to the second iframe (frame2).
     */
    public void switchToFrame2() {
        driver.switchTo().frame(frame2);
    }

    /**
     * Retrieves the text content from the currently focused frame.
     * Assumes the frame contains a body element with text content.
     *
     * @return The text content of the frame's body element
     * @see #switchToFrame1()
     * @see #switchToFrame2()
     */
    public String getFrameText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    /**
     * Switches the driver's focus back to the default (main) content.
     * This should be called after working with frames to return to normal browsing context.
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
}