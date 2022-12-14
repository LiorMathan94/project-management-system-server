package projectManagementSystem.utils;

import java.util.regex.Pattern;

public class InputValidation {
    public enum Field {NAME, EMAIL, PASSWORD}
    public Field field;

    /**
     * Checks if the email is in the appropriate format.
     * @param email
     * @return true if email is valid, otherwise - false.
     */
    public static boolean isValidEmail(String email) {
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email != null && patternMatches(email, regexPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if name consists only of letters and is 2-30 characters long.
     * @param name
     * @return true if name is valid, otherwise - false.
     */
    public static boolean isValidName(String name) {
        String regexPattern = "[ a-zA-Z]{2,30}";
        if (name != null && patternMatches(name, regexPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if password contains at least one letter, at least one digit and is minimum 8 digits long.
     * @param password
     * @return true if name is valid, otherwise - false.
     */
    public static boolean isValidPassword(String password) {
        String regexPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (password != null && patternMatches(password, regexPattern)) {
            return true;
        }
        return false;
    }

    public static boolean isValidItemLabel(String label) {
        String regexPattern = ".{1,20}";
        if (label != null && patternMatches(label, regexPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if fieldToValidate string matches regexPattern
     * @param fieldToValidate
     * @param regexPattern
     * @return true if fieldToValidate matches regexPattern, otherwise - false.
     */
    private static boolean patternMatches(String fieldToValidate, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(fieldToValidate)
                .matches();
    }
}
