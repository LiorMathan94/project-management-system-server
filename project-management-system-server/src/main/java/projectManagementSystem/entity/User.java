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
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preference_id", referencedColumnName = "id")
    private NotificationPreference notificationPreferences;

    public User() {
    }

    private User(String email, String password) {
        this.email = email;
        this.password = password;
        this.notificationPreferences = new NotificationPreference(this);
    }

    public static User createUser(String email, String password){
        return new User(email,password);
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

    public void setEmail(String email) {
        this.email = email;
    }

    public NotificationPreference getNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(NotificationPreference notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public void setId(long id) {
        this.id = id;
    }
}
