package projectManagementSystem.utils;

import java.util.regex.Pattern;

public class InputValidation {
    /**
     * Checks if the email is in the appropriate format.
     *
     * @param email
     * @return true if email is valid, otherwise - false.
     */
    public static boolean isValidEmail(String email) {
        String regexPattern = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        if (email != null && patternMatches(email, regexPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if name consists only of letters and is 2-30 characters long.
     *
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
     * Checks if password contains at least one special character, at least one capital letter, at least one digit, and is 8-20 characters long.
     *
     * @param password
     * @return true if password is valid, otherwise - false.
     */
    public static boolean isValidPassword(String password) {
        String regexPattern = ".{6,20}";
        if (password != null && patternMatches(password, regexPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if label is not null and contains 1-20 characters.
     *
     * @param label
     * @return true if label is valid, otherwise - false.
     */
    public static boolean isValidLabel(String label) {
        String regexPattern = ".{1,20}";
        if (label != null && patternMatches(label, regexPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if fieldToValidate string matches regexPattern
     *
     * @param fieldToValidate
     * @param regexPattern
     * @return true if fieldToValidate matches regexPattern, otherwise - false.
     */
    private static boolean patternMatches(String fieldToValidate, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(fieldToValidate)
                .matches();
    }

    /**
     * @return a string that explains password constraints
     */
    public static String passwordConstraints() {
        return "Password must contain: " +
                "At least 8 characters and at most 20 characters. " +
                "Must not contain any white spaces.";
    }
}
