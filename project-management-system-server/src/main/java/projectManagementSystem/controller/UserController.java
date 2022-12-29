package projectManagementSystem.controller;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.NotificationRequest;
import projectManagementSystem.controller.request.UserRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.LoginMethod;
import projectManagementSystem.service.AuthenticationService;
import projectManagementSystem.service.UserService;
import projectManagementSystem.utils.InputValidation;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authService;
    private static final Logger logger = LogManager.getLogger(UserController.class.getName());

    /**
     * Receives user's email and password. If they are valid sends them to createUser method of UserService, in order to register the user.
     *
     * @param userRequest - UserRequest object, contains inputted user email and password.
     * @return ResponseEntity<Response < UserDTO>> - ResponseEntity.ok (with user details) if action was successful, otherwise - ResponseEntity.badRequest with error message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<Response<UserDTO>> register(@RequestBody UserRequest userRequest) {
        logger.info("in UserController.register()");

        if (!isValidUserRequest(userRequest).getKey()) {
            return ResponseEntity.badRequest().body(Response.failure(isValidUserRequest(userRequest).getValue()));
        }

        try {
            UserDTO user = userService.create(userRequest.getEmail(), userRequest.getPassword(), userRequest.getLoginMethod());
            return ResponseEntity.ok(Response.success(user));
        } catch (IllegalArgumentException e) {
            logger.error("Error occurred during user registration: " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user registration: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Internal Server Error occurred during user registration: " + e);
            return ResponseEntity.internalServerError().body(Response.failure("Error occurred during user registration: " + e.getMessage()));
        }
    }

    private Pair<Boolean, String> isValidUserRequest(UserRequest userRequest) {
        if (userRequest == null) {
            return new Pair<>(false, "Error during user registration. Reason: User register request can't be null.");
        }
        if (!InputValidation.isValidEmail(userRequest.getEmail())) {
            return new Pair<>(false, "Email format is invalid!");
        }
        if (userRequest.getLoginMethod() == LoginMethod.PASSWORD_BASED &&
                !InputValidation.isValidPassword(userRequest.getPassword())) {
            return new Pair<>(false, "Password format is invalid! " + InputValidation.passwordConstraints());
        }

        return new Pair<>(true, "OK");
    }

    /**
     * @param code
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/registerViaGitHub")
    public ResponseEntity<Response<String>> registerViaGitHub(@RequestParam String code) {
        logger.info("in UserController.registerViaGit()");

        try {
            String userEmail = authService.registerViaGit(code);
            if (!InputValidation.isValidEmail(userEmail)) {
                return ResponseEntity.badRequest().body(Response.failure("Email of primary user from git authentication is invalid!"));
            }

            UserDTO userDTO = userService.create(userEmail, null, LoginMethod.GITHUB);
            String token = authService.userLogin(userDTO.getEmail(), null);
            return ResponseEntity.ok(Response.success(token));

        } catch (IllegalArgumentException e) {
            logger.error("Error occurred during user registration: " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user registration: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Internal Server Error occurred during user registration: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Response.failure("Error occurred during user registration: " + e.getMessage()));
        }
    }

    /**
     * Receives user's email and password. If they are valid sends them to userLogin method of UserService, in order to log in the user.
     *
     * @param userRequest - UserRequest object, contains inputted user email and password.
     * @return ResponseEntity<Response < UserDTO>> - ResponseEntity.ok (with user details) if action was successful, otherwise - ResponseEntity.badRequest with error message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<Response<String>> login(@RequestBody UserRequest userRequest) {
        logger.info("in UserController.login()");

        if (userRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("User login credentials cannot be null."));
        }
        if (!InputValidation.isValidEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Response.failure("Email format is invalid!"));
        }
        if (userRequest.getLoginMethod() == LoginMethod.PASSWORD_BASED &&
                !InputValidation.isValidPassword(userRequest.getPassword())) {
            return ResponseEntity.badRequest().body(Response.failure("Password format is invalid!"));
        }

        try {
            String token = authService.userLogin(userRequest.getEmail(), userRequest.getPassword());
            return ResponseEntity.ok(Response.success(token));
        } catch (IllegalArgumentException e) {
            logger.error("Error occurred during user login: " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user login: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Internal Server Error occurred during user login: " + e);
            logger.error("Internal Server Error occurred during user login: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Response.failure("Error occurred during user login: " + e.getMessage()));
        }
    }

    /**
     * Sets the notification preferences for the user with the given id.
     *
     * @param userId              - id of the user that the notifications are set for.
     * @param notificationRequest - NotificationRequest Object, contains user chosen notification of board action events and notification types (email or/and pop up)
     * @return ResponseEntity<Response < UserDTO>> object - contains UserDTO if action was successful, otherwise - contains failure response.
     */
    @RequestMapping(method = RequestMethod.PATCH, path = "/setNotificationPreferences")
    public ResponseEntity<Response<UserDTO>> setNotificationPreferences(@RequestAttribute long userId, @RequestBody NotificationRequest notificationRequest) {
        logger.info("in UserController.setNotificationPreferences()");
        if (notificationRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("Notification request cannot be null"));
        }

        notificationRequest.setUserId(userId);

        try {
            UserDTO user = userService.setNotificationPreferences(notificationRequest);
            return ResponseEntity.ok(Response.success(user));
        } catch (IllegalArgumentException e) {
            logger.error("Error occurred during setting notification preferences for user: " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during setting the notifications preferences: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Internal Server Error occurred during setting notification preferences for user: " + e);
            logger.error("Internal Server Error occurred during setting notification preferences for user: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Response.failure("Error occurred during setting the notifications preferences: " + e.getMessage()));
        }
    }
}
