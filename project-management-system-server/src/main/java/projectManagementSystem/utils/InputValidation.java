package projectManagementSystem.utils;

import projectManagementSystem.controller.response.Response;

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
        if (email != null && email!="" && patternMatches(email, regexPattern)) {
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
     * Checks if password contains at least one special character, at least one capital letter, at least one digit, and is 8-20 characters long.
     * @param password
     * @return Response if password is valid, otherwise - false.
     */
    public static boolean isValidPassword(String password) {
        String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        if (password != null && password != "" && patternMatches(password, regexPattern)) {
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

    public static String passwordConstraints(){
        return "\nPassword must contain:\n" +
                "At least 8 characters and at most 20 characters.\n" +
                "At least one digit.\n" +
                "At least one upper case letter.\n" +
                "At least one lower case letter.\n" +
                "At least one special character which includes !@#$%&*()-+=^.\n" +
                "Must not contain any white spaces.";
    }
}
