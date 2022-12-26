package projectManagementSystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.UserController;
import projectManagementSystem.controller.request.NotificationRequest;
import projectManagementSystem.entity.DTO.UserDTO;
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
     * @return UserDTO object, contains user data that can be shown if registration is successful.
     * @throws IllegalArgumentException - if User with the given email already exists in the database.
     */
    public UserDTO create(String email, String password) {
        logger.info("in UserService.create()");
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email " + email + " already exists.");
        }

        String encryptedPassword = AuthenticationUtils.encryptPassword(password);
        User user = User.createUser(email, encryptedPassword);

        return new UserDTO(userRepository.save(user));
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
            throw new IllegalArgumentException("Could not find user ID: " + notificationRequest.getUserId());
        }

        NotificationPreference preference = new NotificationPreference(user.get());
        preference.setNotificationViaList(notificationRequest.getNotificationViaList());
        preference.setBoardActions(notificationRequest.getBoardActions());

        user.get().setNotificationPreferences(preference);

        return new UserDTO(userRepository.save(user.get()));
    }

    /**
     * Deletes the user that corresponds to userId.
     *
     * @param userId
     */
    public void delete(long userId) {
        logger.info("in UserService.delete()");
        this.userRepository.deleteById(userId);
    }
}
