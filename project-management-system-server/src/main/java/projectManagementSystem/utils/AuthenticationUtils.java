package projectManagementSystem.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthenticationUtils {

    /**
     * Encrypts a given password String using: spring-security-crypto bcrypt.BCryptPasswordEncoder
     *
     * @param password - String, the password that needs to be encrypted
     * @return the encrypted password String.
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    /**
     * Checks if the inputted password matches the encrypted password saved in the database.
     *
     * @param dbPassword    - String, encrypted user password saved in database.
     * @param inputPassword - String, password inputted by user that needs to be checked.
     * @return boolean, true - if the inputted password matches the password in the database; false - otherwise.
     */
    public static boolean isPasswordCorrect(String dbPassword, String inputPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(inputPassword, dbPassword);
    }
}
