package projectManagementSystem.service;

import com.mysql.cj.conf.ConnectionUrlParser;
import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import projectManagementSystem.entity.GitUser;
import projectManagementSystem.entity.LoginMethod;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.AuthenticationUtils;

import java.util.*;

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
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("No registered user with email " + email + " exists.");
        }
        if (user.getLoginMethod() == LoginMethod.PASSWORD_BASED && !AuthenticationUtils.isPasswordCorrect(user.getPassword(), password)) {
            throw new IllegalArgumentException("Password is incorrect!");
        }

        long id = user.getId();
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

    public String registerViaGit(String code) {

        logger.debug("Got request for login through github - " + code);
        if (code.equals("undefined")) {
            throw new IllegalArgumentException("Registration via gitHub was failed");

        }
        RestTemplate rest = new RestTemplate();

        String token = getTokenFromGit(rest,code);

        return (getPrimaryEmailOfUser(rest,token));
    }

    private String getTokenFromGit(RestTemplate rest, String code) {
        String clientSecret = "ba43bf521585a06eb2cab9f837f3612be8da589b";
        String clientId = "71c2e93a422a96bbf6e4";

        String postUrl = "https://github.com/login/oauth/access_token?code=" + code + "&client_id=" + clientId + "&client_secret=" + clientSecret + "&scope=user:email";

        ResponseEntity<String> res = rest.postForEntity(postUrl, null, String.class);
        return (Objects.requireNonNull(res.getBody()).split("&")[0].split("=")[1]);

    }

    private ResponseEntity<GitUser[]> getAuthenticatedUser(RestTemplate rest, String token){

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<GitUser> exchange = rest.exchange("https://api.github.com/user", HttpMethod.GET, entity, GitUser.class);

        GitUser githubUser = exchange.getBody();

        if (githubUser == null) {
            throw new IllegalArgumentException("User doesn't have a GitHub account");
        }

        return rest.exchange("https://api.github.com/user/emails", HttpMethod.GET, entity, GitUser[].class);

    }

    private String getPrimaryEmailOfUser(RestTemplate rest, String token){

        GitUser[] githubUserMail = getAuthenticatedUser(rest,token).getBody();

        if (githubUserMail == null) {
            throw new IllegalArgumentException("User doesn't have a GitHub account");
        }
         String primaryEmail = getPrimaryEmail(githubUserMail);
        if (primaryEmail == null){
            throw new IllegalArgumentException("Extracting User primary email was failed");
        }
        return primaryEmail;
    }


    private String getPrimaryEmail(GitUser[] githubUserMail){

        for (GitUser gitUser : githubUserMail) {
            if (gitUser.isPrimary()) {
                return gitUser.getEmail();
            }
        }
        return null;
    }



}
