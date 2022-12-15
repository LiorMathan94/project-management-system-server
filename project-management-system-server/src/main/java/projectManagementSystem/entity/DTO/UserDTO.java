package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.User;

public class UserDTO {

    private long id;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public UserDTO(){

    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
