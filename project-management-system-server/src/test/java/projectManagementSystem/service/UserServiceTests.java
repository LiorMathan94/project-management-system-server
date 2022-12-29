package projectManagementSystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.NotificationRequest;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.DTO.UserDTO;
import projectManagementSystem.entity.LoginMethod;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.notifications.NotificationVia;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.AuthenticationUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user;
    private UserDTO expectedUserDto;
    private NotificationRequest notificationRequest;

    @Test
    void create_validEmailPasswordLoginMethod_SuccessUserDTO() {
        String email = "validEmail@test.com";
        LoginMethod loginMethod = LoginMethod.PASSWORD_BASED;
        String password = "validPassword";
        user = User.createUser(email,AuthenticationUtils.encryptPassword(password),loginMethod);

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(user);
        expectedUserDto = userService.create(email, password, loginMethod);

        assertNotNull(expectedUserDto,"User was created successfully but method returned UserDTO null");
        assertEquals(email, expectedUserDto.getEmail(), "User was created successfully but email of created user didn't match the email in UserDTO");
        assertEquals(loginMethod, expectedUserDto.getLoginMethod(), "User was created successfully but email of created user didn't match the login method in UserDTO");
    }

    @Test
    void create_userEmailTaken_IllegalArgumentException() {
        String email = "takenEmail@test.com";
        String password = "encryptedPassword";
        LoginMethod loginMethod = LoginMethod.PASSWORD_BASED;
        user = User.createUser(email,password,LoginMethod.PASSWORD_BASED);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userService.create(email,password,loginMethod),"User email is already taken but method didn't throw an IllegalArgumentException");
    }


    @Test
    void setNotificationPreferences_ValidUser_PreferencesSetSuccess() {
        long userId = 1L;
        String email = "validEmail@test.com";

        user = User.createUser(email,"validPassword",LoginMethod.PASSWORD_BASED);
        notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(userId);
        notificationRequest.setNotificationViaList(List.of(NotificationVia.EMAIL));
        notificationRequest.setBoardActions(List.of(BoardAction.CREATE_ITEM, BoardAction.DELETE_ITEM));

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        expectedUserDto = userService.setNotificationPreferences(notificationRequest);

        assertNotNull(expectedUserDto, "Notification preferences were set successfully but method returned null UserDTO");
        assertEquals(email, expectedUserDto.getEmail(),"Notification preferences were set successfully but method didn't return UserDTO with same email");
    }

    @Test
    void setNotificationPreferences_UserFoundNoPreferences_NotificationSetSuccess() {
        long userId = 1L;
        String email = "validEmail@test.com";

        user = User.createUser(email,"validPassword",LoginMethod.PASSWORD_BASED);
        notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(userId);
        notificationRequest.setNotificationViaList(List.of());
        notificationRequest.setBoardActions(List.of());

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        expectedUserDto = userService.setNotificationPreferences(notificationRequest);

        assertNotNull(expectedUserDto, "Notification preferences were set successfully but method returned null UserDTO");
        assertEquals(email, expectedUserDto.getEmail(),"Notification preferences were set successfully but method didn't return UserDTO with same email");
    }

    @Test
    void setNotificationPreferences_userNotFound_ThrowsIllegalArgumentException() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(1L);

        given(userRepository.findById(notificationRequest.getUserId())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,() -> userService.setNotificationPreferences(notificationRequest), "User of the preferences is not found but method doesn't IllegalArgumentException");
    }

    @Test
    void setNotificationPreferences_notificationRequestNull_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class,() -> userService.setNotificationPreferences(null), "Notification request is null but method doesn't throw null pointer exception");
    }
}


