package projectManagementSystem.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidationTests {
    @Test
    @DisplayName("isValidEmail() returns true when email address is valid")
    void isValidEmail_ValidEmail_ReturnsTrue() {
        String validEmail = "lior.mathan1994@gmail.com";
        boolean isValid = InputValidation.isValidEmail(validEmail);
        assertTrue(isValid, String.format("isValidEmail should return true for input: %s", validEmail));
    }

    @Test
    @DisplayName("isValidEmail() returns false when email address is invalid")
    void isValidEmail_InvalidEmail_ReturnsFalse() {
        String invalidEmail = "lior.mathan1994gmail.com";
        boolean isValid = InputValidation.isValidEmail(invalidEmail);
        assertFalse(isValid, String.format("isValidEmail should return false for input: %s", invalidEmail));

        invalidEmail = "lior.mathan1994@gmailcom";
        isValid = InputValidation.isValidEmail(invalidEmail);
        assertFalse(isValid, String.format("isValidEmail should return false for input: %s", invalidEmail));

        invalidEmail = "lior.mathan";
        isValid = InputValidation.isValidEmail(invalidEmail);
        assertFalse(isValid, String.format("isValidEmail should return false for input: %s", invalidEmail));
    }

    @Test
    @DisplayName("isValidName() returns true when name is valid")
    void isValidName_ValidName_ReturnsTrue() {
        String validName = "Lior Mathan";
        boolean isValid = InputValidation.isValidName(validName);
        assertTrue(isValid, String.format("isValidName should return true for input: %s", validName));

        validName = "Lior";
        isValid = InputValidation.isValidName(validName);
        assertTrue(isValid, String.format("isValidName should return true for input: %s", validName));

        validName = "lior mathan";
        isValid = InputValidation.isValidName(validName);
        assertTrue(isValid, String.format("isValidName should return true for input: %s", validName));
    }

    @Test
    @DisplayName("isValidName() returns false when name is invalid")
    void isValidName_InvalidName_ReturnsFalse() {
        String invalidName = "Lior Mathan94";
        boolean isValid = InputValidation.isValidName(invalidName);
        assertFalse(isValid, String.format("isValidName should return false for input: %s", invalidName));

        invalidName = "Lior!!!";
        isValid = InputValidation.isValidName(invalidName);
        assertFalse(isValid, String.format("isValidName should return false for input: %s", invalidName));
    }

    @Test
    @DisplayName("isValidPassword() returns true when password is valid")
    void isValid_ValidPassword_ReturnsTrue() {
        String validPassword = "A1234567";
        boolean isValid = InputValidation.isValidPassword(validPassword);
        assertTrue(isValid, String.format("isValidPassword should return true for input: %s", validPassword));

        validPassword = "Lior956586";
        isValid = InputValidation.isValidPassword(validPassword);
        assertTrue(isValid, String.format("isValidPassword should return true for input: %s", validPassword));

        validPassword = "lior1234567";
        isValid = InputValidation.isValidPassword(validPassword);
        assertTrue(isValid, String.format("isValidPassword should return true for input: %s", validPassword));
    }

    @Test
    @DisplayName("isValidPassword() returns false when password is invalid")
    void isValidPassword_InvalidPassword_ReturnsFalse() {
        String invalidPassword = null;
        boolean isValid = InputValidation.isValidPassword(invalidPassword);
        assertFalse(isValid, String.format("isValidPassword should return false for input: %s", invalidPassword));

        invalidPassword = "L12";
        isValid = InputValidation.isValidPassword(invalidPassword);
        assertFalse(isValid, String.format("isValidPassword should return false for input: %s", invalidPassword));

        invalidPassword = "gr$#%#$%greGerg21424666464";
        isValid = InputValidation.isValidPassword(invalidPassword);
        assertFalse(isValid, String.format("isValidPassword should return false for input: %s", invalidPassword));
    }

    @Test
    @DisplayName("isValidLabel() returns false when label is invalid")
    void isValidLabel_InvalidLabel_ReturnsFalse() {
        String invalidLabel = null;
        boolean isValid = InputValidation.isValidLabel(invalidLabel);
        assertFalse(isValid, String.format("isValidLabel should return false for input: %s", invalidLabel));

        invalidLabel = "";
        isValid = InputValidation.isValidLabel(invalidLabel);
        assertFalse(isValid, String.format("isValidLabel should return false for input: %s", invalidLabel));

        invalidLabel = "gr$#%#$%greGerg21424666464";
        isValid = InputValidation.isValidLabel(invalidLabel);
        assertFalse(isValid, String.format("isValidLabel should return false for input: %s", invalidLabel));
    }

    @Test
    @DisplayName("isValidLabel() returns true when label is valid")
    void isValid_ValidLabel_ReturnsTrue() {
        String validLabel = "A1234567";
        boolean isValid = InputValidation.isValidLabel(validLabel);
        assertTrue(isValid, String.format("isValidLabel should return true for input: %s", validLabel));

        validLabel = "Lior956586";
        isValid = InputValidation.isValidLabel(validLabel);
        assertTrue(isValid, String.format("isValidLabel should return true for input: %s", validLabel));

        validLabel = "lior1234567";
        isValid = InputValidation.isValidLabel(validLabel);
        assertTrue(isValid, String.format("isValidLabel should return true for input: %s", validLabel));
    }
}
