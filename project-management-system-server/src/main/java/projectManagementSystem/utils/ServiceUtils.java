package projectManagementSystem.utils;

import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class ServiceUtils {

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

    /**
     * Creates and returns an authentication token. Consists of an encoding of the given id attached to a random string.
     *
     * @param id - long user id.
     * @return token as String.
     */
    public static String createToken(long id) {
        return Base64.getEncoder().encodeToString((RandomString.make(64) + "-" + id).getBytes());
    }

    /**
     * Extracts and receives user id from token if the id exists in the token.
     *
     * @param token - String token to extract id from.
     * @return long, user id if it exists in token, otherwise - returns -1
     */
    public static long getIdFromToken(String token) {
        String decodedString = new String(Base64.getDecoder().decode(token));
        if (decodedString.split("-")[0] == token) {
            return -1;
        }
        try {
            return Long.parseLong(decodedString.split("-")[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
