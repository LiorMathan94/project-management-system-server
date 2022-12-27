package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.AuthorizedUser;
import projectManagementSystem.entity.Role;
import projectManagementSystem.entity.notifications.NotificationPreference;

public class AuthorizedUserDTO {
    private long id;
    private String email;
    private Role role;
    private NotificationPreference notificationPreference;

    public AuthorizedUserDTO(AuthorizedUser authorizedUser) {
        this.id = authorizedUser.getUser().getId();
        this.email = authorizedUser.getUser().getEmail();
        this.role = authorizedUser.getRole();
        this.notificationPreference = authorizedUser.getUser().getNotificationPreferences();
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public NotificationPreference getNotificationPreference() {
        return notificationPreference;
    }
}
