package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.ServiceUtils;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

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
    public UserDTO createUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email " + email + " already exists.");
        }
        String encryptedPassword = ServiceUtils.encryptPassword(password);
        User user = new User(email, encryptedPassword);
        return new UserDTO(userRepository.save(user));
    }
}
