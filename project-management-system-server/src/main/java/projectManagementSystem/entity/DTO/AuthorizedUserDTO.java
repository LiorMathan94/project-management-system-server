package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.AuthorizedUser;
import projectManagementSystem.entity.Role;
import projectManagementSystem.entity.notifications.NotificationPreference;

public class AuthorizedUserDTO {
    private long id;
    private String email;
    private Role role;
    private NotificationPreference notificationPreference;

    private AuthorizedUserDTO(AuthorizedUser authorizedUser) {
        this.id = authorizedUser.getUser().getId();
        this.email = authorizedUser.getUser().getEmail();
        this.role = authorizedUser.getRole();
        this.notificationPreference = authorizedUser.getUser().getNotificationPreferences();
    }

    /**
     * Creates and returns AuthorizedUserDTO object from given AuthorizedUser object.
     *
     * @param authorizedUser - the AuthorizedUser object from which the AuthorizedUserDTO is created
     * @return AuthorizedUserDTO object, contains authorized user data that can be revealed to client if authorized user is not null, otherwise - returns null.
     */
    public static AuthorizedUserDTO createFromAuthorizedUser(AuthorizedUser authorizedUser){
        if (authorizedUser == null) {
            return null;
        }

        return new AuthorizedUserDTO(authorizedUser);
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
