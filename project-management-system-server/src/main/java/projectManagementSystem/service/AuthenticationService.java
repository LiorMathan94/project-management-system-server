package projectManagementSystem.service;

import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.AuthenticationUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private final Map<Long, String> tokensMap;
    private static final Logger logger = LogManager.getLogger(AuthenticationService.class.getName());

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        tokensMap = new HashMap<>();
    }

    /**
     * Checks if user login credentials are correct and returns the authentication token for the user.
     *
     * @param email    -  user's email inputted during login.
     * @param password -  user's password inputted during login.
     * @return String - the authentication token, if the email and password are correct.
     * @throws IllegalArgumentException - if there is no user with the inputted email in the database, or if password is incorrect.
     */
    public String userLogin(String email, String password) {
        logger.info("in AuthenticationService.userLogin()");
        Optional<User> user = userRepository.findByEmail(email);
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("No registered user with email " + email + " exists.");
        }
        if (!AuthenticationUtils.isPasswordCorrect(user.get().getPassword(), password)) {
            throw new IllegalArgumentException("Password is incorrect!");
        }

        long id = user.get().getId();
        String token = createToken(id);
        tokensMap.put(id, token);

        return token;
    }

    /**
     * Checks if the given token is correct - exists in the tokens map.
     *
     * @param token String authentication token that needs to be checked.
     * @return boolean, true if token format is valid and if it exists in the tokens map, otherwise-false.
     */
    public boolean isTokenCorrect(String token) {
        logger.info("in AuthenticationService.isTokenCorrect()");
        long userId = extractIdFromToken(token);
        return token.equals(tokensMap.get(userId));
    }

    /**
     * Creates and returns an authentication token. Consists of an encoding of the given id attached to a random string.
     *
     * @param id - long user id.
     * @return token as String.
     */
    private String createToken(long id) {
        logger.info("in AuthenticationService.createToken()");
        return Base64.getEncoder().encodeToString((RandomString.make(64) + "-" + id).getBytes());
    }

    /**
     * Extracts and returns user id from token if token format is valid and the id exists in the token.
     *
     * @param token - String token to extract id from.
     * @return long, user id if it exists in token, otherwise - returns -1
     */
    public long extractIdFromToken(String token) {
        logger.info("in AuthenticationService.extractIdFromToken()");
        try {
            String decodedString = new String(Base64.getDecoder().decode(token));
            try {
                return Long.parseLong(decodedString.split("-")[1]);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                return -1;
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            return -1;
        }
    }
}
