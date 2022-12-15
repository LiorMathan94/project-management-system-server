package projectManagementSystem.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
}
