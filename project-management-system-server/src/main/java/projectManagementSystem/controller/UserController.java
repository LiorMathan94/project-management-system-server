package projectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.UserRequest;
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
     * @param userRequest - UserRequest object, contains inputted user email and password.
     * @return ResponseEntity<Response < UserDTO>> - ResponseEntity.ok (with user details) if action was successful, otherwise - ResponseEntity.badRequest with error message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<Response<UserDTO>> userRegister(@RequestBody UserRequest userRequest) {
        if (userRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("Error during user registration. Reason: User register request can't be null."));
        }
        if (!InputValidation.isValidEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Response.failure("Email format is invalid!"));
        }
        if (!InputValidation.isValidPassword(userRequest.getPassword())) {
            return ResponseEntity.badRequest().body(Response.failure("Password format is invalid! " + InputValidation.passwordConstraints()));
        }

        try {
            UserDTO user = userService.createUser(userRequest.getEmail(), userRequest.getPassword());
            return ResponseEntity.ok(Response.success(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user registration: " + e.getMessage()));
        }
    }


    /**
     * Receives user's email and password. If they are valid sends them to userLogin method of UserService, in order to log in the user.
     *
     * @param userRequest - UserRequest object, contains inputted user email and password.
     * @return ResponseEntity<Response < UserDTO>> - ResponseEntity.ok (with user details) if action was successful, otherwise - ResponseEntity.badRequest with error message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<Response<String>> userLogin(@RequestBody UserRequest userRequest) {
        if (userRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("User login credentials can't be null."));
        }
        if (!InputValidation.isValidEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Response.failure("Email format is invalid!"));
        }
        if (!InputValidation.isValidPassword(userRequest.getPassword())) {
            return ResponseEntity.badRequest().body(Response.failure("Password is incorrect!"));
        }

        try {
            String token = authService.userLogin(userRequest.getEmail(), userRequest.getPassword());
            return ResponseEntity.ok(Response.success(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure("Error occurred during user login: " + e.getMessage()));
        }
    }



}
