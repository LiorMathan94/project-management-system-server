package projectManagementSystem.entity;

import javax.persistence.*;

@Entity
public class AuthorizedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AuthorizedUser() {
    }

    /**
     * Constructor for AuthorizedUser
     * @param user
     * @param role
     */
    public AuthorizedUser(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    /**
     * @return authorized user's ID
     */
    public long getId() {
        return user.getId();
    }

    /**
     * @return authorized user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return authorized user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets user.
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets role.
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
