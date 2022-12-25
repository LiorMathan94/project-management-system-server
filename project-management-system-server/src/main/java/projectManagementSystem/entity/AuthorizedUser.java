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

    public AuthorizedUser(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
