package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.UserInBoard;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.UserInBoardRepository;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.AuthenticationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInBoardRepository userInBoardRepository;
    @Autowired
    private BoardRepository boardRepository;

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
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email " + email + " already exists.");
        }

        String encryptedPassword = AuthenticationUtils.encryptPassword(password);
        User user = User.createUser(email, encryptedPassword);

        return new UserDTO(userRepository.save(user));
    }

    /**
     * Deletes the user that corresponds to userId.
     *
     * @param userId
     */
    public void delete(long userId) {
        this.userRepository.deleteById(userId);
    }

    public List<Board> userBoards(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User Id: "+ userId +" isn't exists.");
        }

        return userInBoardRepository.findBoardsByUserId(user).stream().map(UserInBoard::getBoard).collect(Collectors.toList());

    }
}
