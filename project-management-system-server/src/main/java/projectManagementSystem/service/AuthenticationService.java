package projectManagementSystem.service;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.ServiceUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private UserRepository userRepository;

    private final Map<Long, String> tokensMap;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        tokensMap = new HashMap<>();
    }

    public String userLogin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("No registered user with email " + email + " exists.");
        }
        if (!ServiceUtils.isPasswordCorrect(user.get().getPassword(), password)) {
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
        long userId = getIdFromToken(token);
        return token.equals(tokensMap.get(userId));
    }

    /**
     * Creates and returns an authentication token. Consists of an encoding of the given id attached to a random string.
     *
     * @param id - long user id.
     * @return token as String.
     */
    private String createToken(long id) {
        return Base64.getEncoder().encodeToString((RandomString.make(64) + "-" + id).getBytes());
    }

    /**
     * Extracts and returns user id from token if token format is valid and the id exists in the token.
     *
     * @param token - String token to extract id from.
     * @return long, user id if it exists in token, otherwise - returns -1
     */
    public long getIdFromToken(String token) {
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
