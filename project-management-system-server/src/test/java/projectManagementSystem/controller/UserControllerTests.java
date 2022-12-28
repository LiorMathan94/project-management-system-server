package projectManagementSystem.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.UserRequest;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.LoginMethod;
import projectManagementSystem.entity.User;
import projectManagementSystem.service.AuthenticationService;
import projectManagementSystem.service.UserService;

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationService authService;
    @InjectMocks
    private UserController userController;
    private UserDTO userDtoSuccess;
    private UserRequest userRequest;

    @Test
    void register_validUserRequest_ResponseEntityOk() {
        userRequest = new UserRequest("validEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED);
        userDtoSuccess = new UserDTO(User.createUser("validEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED));
        given(userService.create(userRequest.getEmail(), userRequest.getPassword(), userRequest.getLoginMethod())).willReturn(userDtoSuccess);
        assertEquals(200, userController.register(userRequest).getStatusCodeValue(), "Register with valid user email and password did not return ResponseEntity status 200 (OK)");
    }

    @Test
    void register_NullEmail_ResponseEntityBadRequest() {
        assertEquals(400, userController.register(new UserRequest(null, "hgjhj#2$", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with null email did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_NullPassword_ResponseEntityBadRequest() {
        assertEquals(400, userController.register(new UserRequest("hgjhj#2$", null, LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with null password did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_emptyPasswordInput_ResponseEntityBadRequest() {
        assertEquals(400, userController.register(new UserRequest("validEmail@gmail.com", "", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with empty password string did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_emptyEmailInput_ResponseEntityBadRequest() {
        assertEquals(400, userController.register(new UserRequest("", "dsf$%f242", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with empty email string did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_invalidEmailFormat_ResponseEntityBadRequest() {
        assertEquals(400, userController.register(new UserRequest("invalidEmail", "dsf$%f242", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with wrong email format did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_invalidPasswordFormat_ResponseEntityBadRequest() {
        assertEquals(400, userController.register(new UserRequest("validEmail@gmail.com", "bad", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with wrong password format did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_UserEmailTaken_ResponseEntityBadRequest() {
        userRequest = new UserRequest("takenEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED);
        given(userService.create(userRequest.getEmail(), userRequest.getPassword(), LoginMethod.PASSWORD_BASED)).willThrow(new IllegalArgumentException());
        assertEquals(400, userController.register(new UserRequest(userRequest.getEmail(), userRequest.getPassword(), LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with email that is already taken did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void login_validUserRequest_ResponseEntityOk() {
        userRequest = new UserRequest("validEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED);
        given(authService.userLogin(userRequest.getEmail(), userRequest.getPassword())).willReturn("token");
        assertEquals(200, userController.login(userRequest).getStatusCodeValue(), "Login with valid and correct user email and password did not return ResponseEntity status 200 (OK)");
    }

    @Test
    void login_NullEmail_ResponseEntityBadRequest() {
        assertEquals(400, userController.login(new UserRequest(null, "hgjhj#2$", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Login with null email did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void login_NullPassword_ResponseEntityBadRequest() {
        assertEquals(400, userController.login(new UserRequest("hgjhj#2$", null, LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Login with null password did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void login_emptyPasswordInput_ResponseEntityBadRequest() {
        assertEquals(400, userController.login(new UserRequest("validEmail@gmail.com", "", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Login with empty password string did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void login_emptyEmailInput_ResponseEntityBadRequest() {
        assertEquals(400, userController.login(new UserRequest("", "dsf$%f242", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Login with empty email string did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void login_invalidEmailFormat_ResponseEntityBadRequest() {
        assertEquals(400, userController.login(new UserRequest("invalidEmail", "dsf$%f242", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Login with wrong email format did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void login_invalidPasswordFormat_ResponseEntityBadRequest() {
        assertEquals(400, userController.login(new UserRequest("validEmail@gmail.com", "bad", LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Login with wrong password format did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_UserEmailNotExists_ResponseEntityBadRequest() {
        userRequest = new UserRequest("wrongEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED);
        given(authService.userLogin(userRequest.getEmail(), userRequest.getPassword())).willThrow(new IllegalArgumentException());
        assertEquals(400, userController.login(new UserRequest(userRequest.getEmail(), userRequest.getPassword(), userRequest.getLoginMethod())).getStatusCodeValue(), "Login with email that doesn't exist in the data base did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_UserPasswordIncorrect_ResponseEntityBadRequest() {
        userRequest = new UserRequest("validEmail@gmail.com", "gdgdg%^46122", LoginMethod.PASSWORD_BASED);
        given(authService.userLogin(userRequest.getEmail(), userRequest.getPassword())).willThrow(new IllegalArgumentException());
        assertEquals(400, userController.login(new UserRequest(userRequest.getEmail(), userRequest.getPassword(), userRequest.getLoginMethod())).getStatusCodeValue(), "Login with incorrect password did not return ResponseEntity status 400 (Bad Request)");
    }

    //TODO: add tests for setNotifications and delete methods
}
