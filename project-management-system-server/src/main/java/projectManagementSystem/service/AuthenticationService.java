package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.ServiceUtils;

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
        String token = ServiceUtils.createToken(id);
        tokensMap.put(id,token);
        return token;
    }
}
