package com.demoqa.pages.elements;

import com.demoqa.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

/**
 * Page Object class for interacting with the CheckBox page on demoqa.com.
 * Provides methods to manage and verify checkbox operations including:
 * <ul>
 *   <li>Expanding the checkbox tree</li>
 *   <li>Selecting checkboxes</li>
 *   <li>Getting selection results</li>
 * </ul>
 */
public class CheckBoxPage extends BasePage {

    @FindBy(css = "button[title='Expand all']")
    WebElement expandAllButton;

    @FindBy(xpath = "//span[text()='Home']")
    WebElement homeCheckBoxLabel;

    @FindBy(css = ".rct-icon-check")
    List<WebElement> selectedCheckboxes;

    @FindBy(id = "result")
    WebElement resultText;

    /**
     * Constructs a new CheckBoxPage instance.
     * @param driver The WebDriver instance used for page interactions
     */
    public CheckBoxPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Expands the entire checkbox tree to show all available options.
     * Waits for the expand button to be visible before clicking.
     */
    public void expandAll() {
        wait.until(ExpectedConditions.visibilityOf(expandAllButton));
        expandAllButton.click();
    }

    /**
     * Selects the main "Home" checkbox which may select all child checkboxes.
     * The actual behavior depends on the page implementation (whether selection cascades to children).
     */
    public void selectHomeCheckBox() {
        homeCheckBoxLabel.click();
    }

    /**
     * Gets the count of currently selected checkboxes.
     * @return Number of checkboxes with selected state
     */
    public int getSelectedCheckboxesCount() {
        return selectedCheckboxes.size();
    }

    /**
     * Gets the text result showing the selected checkbox items.
     * @return Result text containing all selected options
     */
    public String getResultText() {
        return resultText.getText();
    }
}