package projectManagementSystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.UserRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.Role;
import projectManagementSystem.entity.User;
import projectManagementSystem.service.AuthenticationService;
import projectManagementSystem.service.UserRoleService;
import projectManagementSystem.service.UserService;
import projectManagementSystem.utils.InputValidation;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private UserRoleService userRoleService;
    private static final Logger logger = LogManager.getLogger(BoardController.class.getName());


    @RequestMapping(method = RequestMethod.POST, path = "/getBoards")
    public ResponseEntity<Response<List<Board>>> getBoardsByUserId(@RequestBody long userId) {
        logger.info("in BoardController.create()");
        try {
            List<Board> boards = userService.userBoards(userId);
            return ResponseEntity.ok(Response.success(boards));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Receives user's email and password. If they are valid sends them to createUser method of UserService, in order to register the user.
     *
     * @param userRequest - UserRequest object, contains inputted user email and password.
     * @return ResponseEntity<Response < UserDTO>> - ResponseEntity.ok (with user details) if action was successful, otherwise - ResponseEntity.badRequest with error message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<Response<UserDTO>> register(@RequestBody UserRequest userRequest) {
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
            UserDTO user = userService.create(userRequest.getEmail(), userRequest.getPassword());
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
    public ResponseEntity<Response<String>> login(@RequestBody UserRequest userRequest) {
        if (userRequest == null) {
            return ResponseEntity.badRequest().body(Response.failure("User login credentials cannot be null."));
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

    /**
     * Deletes the user that corresponds to userId and its existing roles.
     *
     * @param userId
     * @return ResponseEntity<Response < Void>>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public ResponseEntity<Response<Void>> delete(@RequestParam long userId) {
        try {
            this.userRoleService.deleteByUser(userId);
            userService.delete(userId);
            return ResponseEntity.ok(Response.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure("Error occurred while trying to delete user #"
                    + userId + ": " + e.getMessage()));
        }
    }
}
