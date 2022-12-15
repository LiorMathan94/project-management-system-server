package projectManagementSystem.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.UserRegisterRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.service.UserService;
import projectManagementSystem.utils.InputValidation;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<Response<String>> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("Error during user registration. Reason: User register request can't be null."));
        }
        if (!InputValidation.isValidEmail(userRegisterRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Response.failure("Email format is invalid!"));
        }
        if (!InputValidation.isValidPassword(userRegisterRequest.getPassword())) {
            return ResponseEntity.badRequest().body(Response.failure("Password format is invalid! " + InputValidation.passwordConstraints()));
        }
        try {
            String responseData = userService.createUser(userRegisterRequest.getEmail(), userRegisterRequest.getPassword());
            return ResponseEntity.ok(Response.success(responseData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user registration: " + e.getMessage()));
        }
    }

}
