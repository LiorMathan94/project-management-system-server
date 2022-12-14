package projectManagementSystem.service;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.response.Response;

@Service
public class UserService {
    public Response<String> createUser(String email, String password) {
        return Response.success(email);
    }
}
