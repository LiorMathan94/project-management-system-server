package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.User;

public class UserDTO {
    private String email;

    public UserDTO(User user) {
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
