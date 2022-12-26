package projectManagementSystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.UserRequest;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.User;
import projectManagementSystem.service.UserService;
import projectManagementSystem.utils.InputValidation;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import javax.security.auth.login.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private User validUser;
    private User userInvalidPassword;
    private User userInvalidEmail;
    private UserDTO userDtoSuccess;
    private UserRequest validUserRequest;

    @BeforeEach
    void setUp() {
        validUser = User.createUser("2@gmail.com", "22222222");
        userInvalidEmail = User.createUser("invalidEmail", "gdgh65&*67fhg");
        userInvalidPassword = User.createUser("valid.email@gmail.com", "bad");
        userDtoSuccess = new UserDTO(validUser);
        validUserRequest = new UserRequest("2@gmail.com", "22222222");
    }

    @Test
    void register_validUserRequest_ResponseEntityOk(){
        given(userService.create(validUserRequest.getEmail(), validUserRequest.getPassword())).willReturn(userDtoSuccess);
        assertEquals(200, userController.register(validUserRequest).getStatusCodeValue(), "Register with valid user email and password did not return ResponseEntity status 200");
    }
}
