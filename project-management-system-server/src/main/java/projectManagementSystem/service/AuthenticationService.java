package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.ServiceUtils;

import java.util.Optional;

@Service
public class AuthenticationService {

    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String userLogin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("No registered user with email " + email + " exists.");
        }
        if(!ServiceUtils.isPasswordCorrect(user.get().getPassword(),password)){
            throw new IllegalArgumentException("Password is incorrect!");
        }
        return createToken(user.get().getId(),user.get().getEmail());
    }

    private String createToken(long id, String email){
        return "token" + email;
    }
}
