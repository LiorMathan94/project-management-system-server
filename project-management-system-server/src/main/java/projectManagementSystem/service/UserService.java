package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(String email, String password) {
        User user = new User(email, password);
        return new UserDTO(userRepository.save(user));
    }
}
