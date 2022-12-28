package projectManagementSystem.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import projectManagementSystem.controller.request.NotificationRequest;
import projectManagementSystem.controller.request.UserRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.LoginMethod;
import projectManagementSystem.entity.User;
import projectManagementSystem.service.AuthenticationService;
import projectManagementSystem.service.UserService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private UserService userServiceMock;
    @Mock
    private AuthenticationService authServiceMock;
    @InjectMocks
    private UserController userController;
    private UserDTO expectedUserDto;
    private UserRequest userRequest;
    private ResponseEntity<Response<UserDTO>> response;

    @Test
    void register_validUserRequest_ResponseEntityOk() {
        userRequest = new UserRequest("validEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED);
        expectedUserDto = UserDTO.createFromUser(User.createUser("validEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED));
        given(userServiceMock.create(userRequest.getEmail(), userRequest.getPassword(), userRequest.getLoginMethod())).willReturn(expectedUserDto);
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
        given(userServiceMock.create(userRequest.getEmail(), userRequest.getPassword(), LoginMethod.PASSWORD_BASED)).willThrow(new IllegalArgumentException());
        assertEquals(400, userController.register(new UserRequest(userRequest.getEmail(), userRequest.getPassword(), LoginMethod.PASSWORD_BASED)).getStatusCodeValue(), "Register with email that is already taken did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void login_validUserRequest_ResponseEntityOk() {
        userRequest = new UserRequest("validEmail@gmail.com", "dsf$%f242", LoginMethod.PASSWORD_BASED);
        given(authServiceMock.userLogin(userRequest.getEmail(), userRequest.getPassword())).willReturn("token");
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
        given(authServiceMock.userLogin(userRequest.getEmail(), userRequest.getPassword())).willThrow(new IllegalArgumentException());
        assertEquals(400, userController.login(new UserRequest(userRequest.getEmail(), userRequest.getPassword(), userRequest.getLoginMethod())).getStatusCodeValue(), "Login with email that doesn't exist in the data base did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void register_UserPasswordIncorrect_ResponseEntityBadRequest() {
        userRequest = new UserRequest("validEmail@gmail.com", "gdgdg%^46122", LoginMethod.PASSWORD_BASED);
        given(authServiceMock.userLogin(userRequest.getEmail(), userRequest.getPassword())).willThrow(new IllegalArgumentException());
        assertEquals(400, userController.login(new UserRequest(userRequest.getEmail(), userRequest.getPassword(), userRequest.getLoginMethod())).getStatusCodeValue(), "Login with incorrect password did not return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void setNotificationPreferences_UserNotExistsRequestValid_ResponseEntityBadRequest() {
        NotificationRequest notificationRequest = new NotificationRequest();
        given(userServiceMock.setNotificationPreferences(notificationRequest)).willThrow(new IllegalArgumentException());

        ResponseEntity<Response<UserDTO>> response = userController.setNotificationPreferences(1L, notificationRequest);
        assertNull(Objects.requireNonNull(response.getBody()).getData(), "Set notification preferences with non existing user id didn't return response with null in the data field of the body.");
        assertEquals(400, response.getStatusCodeValue(), "Set notification preferences with non existing user id didn't return ResponseEntity status 400 (Bad Request).");
    }

    @Test
    void setNotificationPreferences_RequestNull_ResponseEntityBadRequest() {
        response = userController.setNotificationPreferences(1L, null);

        assertNull(Objects.requireNonNull(response.getBody()).getData(), "Set Notification preferences with null request didn't return response with null in the data field of the body.");
        assertEquals(400, response.getStatusCodeValue(), "Set Notification preferences with null request didn't return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void setNotificationPreferences_UserExistsRequestValid_ResponseEntityResponseOk() {
        NotificationRequest notificationRequest = new NotificationRequest();
        expectedUserDto = UserDTO.createFromUser(new User());
        given(userServiceMock.setNotificationPreferences(notificationRequest)).willReturn(expectedUserDto);

        response = userController.setNotificationPreferences(1L, notificationRequest);
        assertEquals(Response.success(expectedUserDto).getData(), Objects.requireNonNull(userController.setNotificationPreferences(1L, notificationRequest).getBody()).getData(), "Set notification preferences with existing user exists and valid notification request didn't return updated user data");
        assertEquals(200, response.getStatusCodeValue(), "Set notification preferences with existing user exists and valid notification request didn't return ResponseEntity.ok (200) ");
    }
}
