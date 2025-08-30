package com.demoqa.functional;

import com.demoqa.base.BaseTest;
import com.demoqa.config.ConfigurationManager;
import com.demoqa.pages.forms.PracticeFormPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;

/**
 * Functional test class for verifying form submission functionality.
 * Contains test cases for the Automation Practice Form with various input fields.
 *
 * <p>Tests cover:
 * <ul>
 *   <li>Complete form submission with all field types</li>
 *   <li>File upload functionality</li>
 *   <li>Form validation and submission confirmation</li>
 * </ul>
 *
 * <p>Extends BaseTest to inherit common test setup and browser management.
 */
public class FormsTests extends BaseTest {

    /**
     * Page object for interacting with the Practice Form page
     */
    private PracticeFormPage practiceFormPage;

    /**
     * Path to test image file used for upload testing
     */
    private static final String TEST_IMAGE_PATH = new File("src/test/resources/test_data/test_image.png").getAbsolutePath();

    /**
     * Initializes page objects before each test method execution.
     * Creates instance of PracticeFormPage bound to the current WebDriver.
     */
    @BeforeMethod
    public void setupPages() {
        practiceFormPage = new PracticeFormPage(driver);
    }

    /**
     * Tests complete form submission with all possible field types.
     * Verifies:
     * <ul>
     *   <li>All form fields can be successfully filled</li>
     *   <li>File upload works correctly</li>
     *   <li>Form can be submitted</li>
     *   <li>Confirmation modal appears after submission</li>
     *   <li>Modal contains expected title</li>
     *   <li>Modal can be closed</li>
     * </ul>
     *
     * <p>Test data includes:
     * <ul>
     *   <li>Name: John Doe</li>
     *   <li>Email: john.doe@example.com</li>
     *   <li>Gender: Male</li>
     *   <li>Phone: 1234567890</li>
     *   <li>Date of Birth: 01 Jan 2000</li>
     *   <li>Subject: Maths</li>
     *   <li>Hobby: Sports</li>
     *   <li>Image: test_image.png</li>
     *   <li>Address: 123 Main St, New York</li>
     * </ul>
     */
    @Test
    public void testFormSubmissionWithAllFields() {
        driver.get(new ConfigurationManager().getBaseUrl() + "/automation-practice-form");


        practiceFormPage.enterFirstName("John");
        practiceFormPage.enterLastName("Doe");
        practiceFormPage.enterEmail("john.doe@example.com");
        practiceFormPage.selectGender("Male");
        practiceFormPage.enterPhoneNumber("1234567890");
        practiceFormPage.setDateOfBirthSimple("01/15/1990");
        practiceFormPage.enterSubjects("Maths");
        practiceFormPage.selectHobbies(List.of("Sports"));
        practiceFormPage.uploadPicture(TEST_IMAGE_PATH);
        practiceFormPage.enterAddress("123 Main St");
        practiceFormPage.submitForm();


        Assert.assertTrue(practiceFormPage.isModalDisplayed(),
                "Confirmation modal should be visible after form submission");
        Assert.assertEquals(practiceFormPage.getModalTitle(),
                "Thanks for submitting the form",
                "Modal title should match expected confirmation message");


        practiceFormPage.closeModal();
    }
}