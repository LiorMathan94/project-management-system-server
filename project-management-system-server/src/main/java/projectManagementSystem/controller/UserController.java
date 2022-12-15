package projectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.UserLoginRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.service.AuthenticationService;
import projectManagementSystem.service.UserService;
import projectManagementSystem.utils.InputValidation;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authService;

    public UserController() {
    }

    /**
     * Receives user's email and password. If they are valid sends them to createUser method of UserService, in order to register the user.
     *
     * @param userLoginRequest - UserLoginRequest object, contains inputted user email and password.
     * @return ResponseEntity<Response < UserDTO>> - ResponseEntity.ok (with user details) if action was successful, otherwise - ResponseEntity.badRequest with error message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<Response<UserDTO>> userRegister(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("Error during user registration. Reason: User register request can't be null."));
        }
        if (!InputValidation.isValidEmail(userLoginRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Response.failure("Email format is invalid!"));
        }
        if (!InputValidation.isValidPassword(userLoginRequest.getPassword())) {
            return ResponseEntity.badRequest().body(Response.failure("Password format is invalid! " + InputValidation.passwordConstraints()));
        }

        try {
            UserDTO user = userService.createUser(userLoginRequest.getEmail(), userLoginRequest.getPassword());
            return ResponseEntity.ok(Response.success(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user registration: " + e.getMessage()));
        }
    }


    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<Response<String>> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("User login credentials can't be null."));
        }
        if (!InputValidation.isValidEmail(userLoginRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Response.failure("Email format is invalid!"));
        }
        if (!InputValidation.isValidPassword(userLoginRequest.getPassword())) {
            return ResponseEntity.badRequest().body(Response.failure("Password is incorrect!"));
        }

        try {
            String token = authService.userLogin(userLoginRequest.getEmail(), userLoginRequest.getPassword());
            return ResponseEntity.ok(Response.success(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user login: " + e.getMessage()));
        }
    }



}
