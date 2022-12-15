package projectManagementSystem.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ServiceUtils {

    /**
     * Encrypts a given string using
     * @param password - String, the password that needs to be encrypted
     * @return the encrypted String.
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
