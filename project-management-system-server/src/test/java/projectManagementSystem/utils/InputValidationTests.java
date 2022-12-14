package projectManagementSystem.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationTests {
    @Test
    @DisplayName("isValidEmail() returns true when email address is valid")
    void isValidEmail_ValidEmail_ReturnsTrue() {
        String validEmail = "lior.mathan1994@gmail.com";
        boolean isValid = InputValidation.isValidEmail(validEmail);
        assertEquals(true, isValid,
                String.format("isValidEmail should return true for input: %s", validEmail));
    }

    @Test
    @DisplayName("isValidEmail() returns false when email address is invalid")
    void isValidEmail_InvalidEmail_ReturnsFalse() {
        String invalidEmail = "lior.mathan1994gmail.com";
        boolean isValid = InputValidation.isValidEmail(invalidEmail);
        assertEquals(false, isValid,
                String.format("isValidEmail should return false for input: %s", invalidEmail));

        invalidEmail = "lior.mathan1994@gmailcom";
        isValid = InputValidation.isValidEmail(invalidEmail);
        assertEquals(false, isValid,
                String.format("isValidEmail should return false for input: %s", invalidEmail));

        invalidEmail = "lior.mathan";
        isValid = InputValidation.isValidEmail(invalidEmail);
        assertEquals(false, isValid,
                String.format("isValidEmail should return false for input: %s", invalidEmail));
    }

    @Test
    @DisplayName("isValidName() returns true when name is valid")
    void isValidName_ValidName_ReturnsTrue() {
        String validName = "Lior Mathan";
        boolean isValid = InputValidation.isValidName(validName);
        assertEquals(true, isValid,
                String.format("isValidName should return true for input: %s", validName));

        validName = "Lior";
        isValid = InputValidation.isValidName(validName);
        assertEquals(true, isValid,
                String.format("isValidName should return true for input: %s", validName));

        validName = "lior mathan";
        isValid = InputValidation.isValidName(validName);
        assertEquals(true, isValid,
                String.format("isValidName should return true for input: %s", validName));
    }

    @Test
    @DisplayName("isValidName() returns false when name is invalid")
    void isValidName_InvalidName_ReturnsFalse() {
        String invalidName = "Lior Mathan94";
        boolean isValid = InputValidation.isValidName(invalidName);
        assertEquals(false, isValid,
                String.format("isValidName should return false for input: %s", invalidName));

        invalidName = "Lior!!!";
        isValid = InputValidation.isValidName(invalidName);
        assertEquals(false, isValid,
                String.format("isValidName should return false for input: %s", invalidName));
    }

    @Test
    @DisplayName("isValidPassword() returns true when password is valid")
    void isValidPassword_ValidName_ReturnsTrue() {
        String validPassword = "A1234567";
        boolean isValid = InputValidation.isValidPassword(validPassword);
        assertEquals(true, isValid,
                String.format("isValidPassword should return true for input: %s", validPassword));

        validPassword = "Lior956586";
        isValid = InputValidation.isValidPassword(validPassword);
        assertEquals(true, isValid,
                String.format("isValidPassword should return true for input: %s", validPassword));

        validPassword = "lior1234567";
        isValid = InputValidation.isValidPassword(validPassword);
        assertEquals(true, isValid,
                String.format("isValidPassword should return true for input: %s", validPassword));
    }

    @Test
    @DisplayName("isValidPassword() returns false when password is invalid")
    void isValidPassword_InvalidName_ReturnsFalse() {
        String invalidPassword = "A123456!";
        boolean isValid = InputValidation.isValidPassword(invalidPassword);
        assertEquals(false, isValid,
                String.format("isValidPassword should return false for input: %s", invalidPassword));

        invalidPassword = "L1234";
        isValid = InputValidation.isValidPassword(invalidPassword);
        assertEquals(false, isValid,
                String.format("isValidPassword should return false for input: %s", invalidPassword));

        invalidPassword = "123456789";
        isValid = InputValidation.isValidPassword(invalidPassword);
        assertEquals(false, isValid,
                String.format("isValidPassword should return false for input: %s", invalidPassword));
    }
}
