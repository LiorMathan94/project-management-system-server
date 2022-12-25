package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.AuthorizedUser;

public class AuthorizedUserDTO {
    private long id;
    private String email;

    public AuthorizedUserDTO(AuthorizedUser authorizedUser) {
        this.id = authorizedUser.getUser().getId();
        this.email = authorizedUser.getUser().getEmail();
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}
