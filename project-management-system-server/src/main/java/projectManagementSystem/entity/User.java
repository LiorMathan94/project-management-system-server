package projectManagementSystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    public User() {

    }

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Creates and returns a User.
     *
     * @param email    - String, email of the user.
     * @param password - String, password of the user.
     * @return the created User object.
     */
    public static User createUser(String email, String password) {
        User user = new User(email, password);
        return user;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
