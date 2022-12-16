package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.User;

public class UserDTO {
    private String email;

    private UserDTO(String email) {
        this.email = email;
    }

    public static UserDTO createUserDtoFromUser(User user) {
        return new UserDTO(user.getEmail());
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
