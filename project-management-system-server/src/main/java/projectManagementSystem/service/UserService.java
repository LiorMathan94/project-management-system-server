package projectManagementSystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.NotificationRequest;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.LoginMethod;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.notifications.NotificationPreference;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.AuthenticationUtils;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Adds a User to database (if he doesn't already exist), after encrypting his password.
     *
     * @param email    - String, user email inputted during registration.
     * @param password - String, user password user email inputted during registration.
     * @param loginMethod - Enum LoginMethod, specifies the login method the user will use after registration.
     * @return UserDTO object, contains user data that can be shown if registration is successful.
     * @throws IllegalArgumentException - if User with the given email already exists in the database.
     */
    public UserDTO create(String email, String password, LoginMethod loginMethod) {
        logger.info("in UserService.create()");

        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            return createNewUser(email, password, loginMethod);
        }

        if (user.get().getLoginMethod() == LoginMethod.PASSWORD_BASED) {
            logger.error("User with email " + email + " already exists.");
            throw new IllegalArgumentException("User with email " + email + " already exists.");
        }

        return UserDTO.createFromUser(user.get());
    }

    /**
     * Adds a User to database (if he doesn't already exist), after encrypting his password.
     *
     * @param email    - String, user email inputted during registration.
     * @param password - String, user password user email inputted during registration.
     * @param loginMethod - Enum LoginMethod, specifies the login method the user will use after registration.
     * @return UserDTO object, contains user data that can be shown if registration is successful.
     * @throws IllegalArgumentException - if User with the given email already exists in the database.
     */
    private UserDTO createNewUser(String email, String password, LoginMethod loginMethod){
        logger.info("in UserService.createNewUser()");
        String encryptedPassword = password != null ? AuthenticationUtils.encryptPassword(password) : null;
        User user = User.createUser(email, encryptedPassword, loginMethod);

        User savedUser = userRepository.save(user);
        return UserDTO.createFromUser(savedUser);
    }

    /**
     * Sets user's notifications preferences.
     *
     * @param notificationRequest
     * @return the user's DTO version
     */
    public UserDTO setNotificationPreferences(NotificationRequest notificationRequest) {
        logger.info("in UserService.setNotificationPreferences()");
        Optional<User> user = userRepository.findById(notificationRequest.getUserId());

        if (!user.isPresent()) {
            logger.error("Could not find user ID: " + notificationRequest.getUserId());
            throw new IllegalArgumentException("Could not find user ID: " + notificationRequest.getUserId());
        }

        NotificationPreference preference = new NotificationPreference(user.get());
        preference.setNotificationViaList(notificationRequest.getNotificationViaList());
        preference.setBoardActions(notificationRequest.getBoardActions());

        user.get().setNotificationPreferences(preference);

        return UserDTO.createFromUser(userRepository.save(user.get()));
    }

}
