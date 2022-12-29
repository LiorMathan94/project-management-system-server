package projectManagementSystem.entity;

import projectManagementSystem.entity.notifications.NotificationPreference;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preference_id", referencedColumnName = "id")
    private NotificationPreference notificationPreferences;
    @Enumerated(EnumType.STRING)
    private LoginMethod loginMethod;

    public User() {
    }

    /**
     * Constructor for User.
     * @param email
     * @param password
     */
    private User(String email, String password, LoginMethod loginMethod) {
        this.email = email;
        this.password = password;
        this.notificationPreferences = new NotificationPreference(this);
        this.loginMethod = loginMethod;
    }

    /**
     * Static factory method for User.
     * @param email
     * @param password
     * @return the new user
     */
    public static User createUser(String email, String password, LoginMethod loginMethod) {
        return new User(email, password, loginMethod);
    }

    /**
     * @return user's ID
     */
    public long getId() {
        return id;
    }

    /**
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's email.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return user's notifications preferences
     */
    public NotificationPreference getNotificationPreferences() {
        return notificationPreferences;
    }

    /**
     * Sets user's notifications preferences.
     * @param notificationPreferences
     */
    public void setNotificationPreferences(NotificationPreference notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LoginMethod getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(LoginMethod loginMethod) {
        this.loginMethod = loginMethod;
    }
}
