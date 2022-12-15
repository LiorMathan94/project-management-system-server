package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.InputValidation;
import projectManagementSystem.utils.ServiceUtils;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(String email, String password) {
        String encryptedPassword = ServiceUtils.encryptPassword(password);
        User user = new User(email, encryptedPassword);
        return new UserDTO(userRepository.save(user));
    }
}
