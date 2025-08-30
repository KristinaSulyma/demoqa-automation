package com.demoqa.functional;

import com.demoqa.base.BaseTest;
import com.demoqa.config.ConfigurationManager;
import com.demoqa.pages.elements.CheckBoxPage;
import com.demoqa.pages.elements.RadioButtonPage;
import com.demoqa.pages.elements.TextBoxPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Functional test class for verifying various HTML elements functionality.
 * Contains test cases for:
 * - Text Box form submission
 * - Check Box selection
 * - Radio Button selection
 *
 * <p>Test scenarios cover:
 * <ul>
 *   <li>Form input and submission validation</li>
 *   <li>Tree-structured checkbox selection</li>
 *   <li>Radio button state and selection</li>
 * </ul>
 *
 * <p>Extends BaseTest to inherit common test setup and browser management.
 */
public class ElementsTests extends BaseTest {

    /**
     * Page object for interacting with Text Box elements
     */
    private TextBoxPage textBoxPage;

    /**
     * Page object for interacting with Check Box elements
     */
    private CheckBoxPage checkBoxPage;

    /**
     * Page object for interacting with Radio Button elements
     */
    private RadioButtonPage radioButtonPage;

    /**
     * Initializes all page objects before each test method execution.
     * Creates instances of:
     * - TextBoxPage
     * - CheckBoxPage
     * - RadioButtonPage
     * bound to the current WebDriver instance.
     */
    @BeforeMethod
    public void setupPages() {
        textBoxPage = new TextBoxPage(driver);
        checkBoxPage = new CheckBoxPage(driver);
        radioButtonPage = new RadioButtonPage(driver);
    }

    /**
     * Tests Text Box form submission functionality.
     * Verifies:
     * <ul>
     *   <li>Form can be filled with test data</li>
     *   <li>Form can be successfully submitted</li>
     *   <li>Output section appears after submission</li>
     * </ul>
     */
    @Test
    public void testTextBoxFormSubmission() {
        driver.get(new ConfigurationManager().getBaseUrl() + "/text-box");
        textBoxPage.fillForm("John Doe", "john.doe@example.com", "123 Main St", "456 Park Ave");
        textBoxPage.submitForm();
        Assert.assertTrue(textBoxPage.isOutputDisplayed(),
                "Output box should be visible after form submission");
    }

    /**
     * Tests Check Box selection functionality in tree structure.
     * Verifies:
     * <ul>
     *   <li>All nodes can be expanded</li>
     *   <li>Parent checkbox selects all child nodes</li>
     *   <li>Correct number of checkboxes are selected</li>
     *   <li>Result text reflects the selection</li>
     * </ul>
     */
    @Test
    public void testCheckBoxSelection() {
        driver.get(new ConfigurationManager().getBaseUrl() + "/checkbox");
        checkBoxPage.expandAll();
        checkBoxPage.selectHomeCheckBox();
        Assert.assertEquals(checkBoxPage.getSelectedCheckboxesCount(), 17,
                "All 17 child nodes should be selected when choosing Home");
        Assert.assertTrue(checkBoxPage.getResultText().contains("home"),
                "Selection result should include 'home'");
    }

    /**
     * Tests Radio Button selection functionality.
     * Verifies:
     * <ul>
     *   <li>Radio buttons can be selected individually</li>
     *   <li>Result text updates with selection</li>
     *   <li>Disabled radio button remains unselectable</li>
     *   <li>Selection state changes correctly</li>
     * </ul>
     */
    @Test
    public void testRadioButtonSelection() {
        driver.get(new ConfigurationManager().getBaseUrl() + "/radio-button");
        radioButtonPage.clickYesRadioButton();
        Assert.assertTrue(radioButtonPage.getResultText().contains("Yes"),
                "Result should reflect 'Yes' selection");
        radioButtonPage.clickImpressiveRadioButton();
        Assert.assertTrue(radioButtonPage.getResultText().contains("Impressive"),
                "Result should reflect 'Impressive' selection");
        Assert.assertFalse(radioButtonPage.isNoRadioButtonEnabled(),
                "'No' radio button should remain disabled as per requirements");
    }
}