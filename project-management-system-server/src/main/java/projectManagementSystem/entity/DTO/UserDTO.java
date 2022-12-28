package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.User;

public class UserDTO {
    private String email;
    private UserDTO(User user) {
        this.email = user.getEmail();
    }

    /**
     * Creates and returns UserDTO object from given User object.
     *
     * @param user - the User object from which the UserDTO is created
     * @return UserDTO object, contains user data that can be revealed to client if item is not null, otherwise - returns null.
     */
    public static UserDTO createUserDTOFromUser(User user){
        if(user==null){
            return null;
        }
        return new UserDTO(user);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
