package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.AuthorizedUser;
import projectManagementSystem.entity.Role;

public class AuthorizedUserDTO {
    private long id;
    private String email;
    private Role role;

    public AuthorizedUserDTO(AuthorizedUser authorizedUser) {
        this.id = authorizedUser.getUser().getId();
        this.email = authorizedUser.getUser().getEmail();
        this.role = authorizedUser.getRole();
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
}
