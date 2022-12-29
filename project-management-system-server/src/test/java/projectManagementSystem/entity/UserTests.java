package projectManagementSystem.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectManagementSystem.entity.notifications.NotificationPreference;
import projectManagementSystem.entity.notifications.NotificationVia;


public class UserTests {
    private User user;

    @BeforeEach
    void beforeEach() {
        user = User.createUser("test@example.com", "password", LoginMethod.PASSWORD_BASED);
    }

    @Test
    public void testCreateUser() {
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(LoginMethod.PASSWORD_BASED, user.getLoginMethod());
    }

    @Test
    public void testSetEmail() {
        User user = User.createUser("test@example.com", "password", LoginMethod.PASSWORD_BASED);
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    public void testGetNotificationPreferences() {
        User user = User.createUser("test@example.com", "password", LoginMethod.PASSWORD_BASED);
        NotificationPreference prefs = user.getNotificationPreferences();
        assertNotNull(prefs);
    }

    @Test
    public void testSetLoginMethod() {
        assertEquals(LoginMethod.PASSWORD_BASED, user.getLoginMethod());
        user.setLoginMethod(LoginMethod.GITHUB);
        assertEquals(LoginMethod.GITHUB, user.getLoginMethod());
    }

    @Test
    public void testSetNotificationPreferences() {
        NotificationPreference preferences = new NotificationPreference(user);
        preferences.addNotificationVia(NotificationVia.EMAIL);
        user.setNotificationPreferences(preferences);
        assertTrue(user.getNotificationPreferences().getNotificationViaList().contains(NotificationVia.EMAIL));
    }
}