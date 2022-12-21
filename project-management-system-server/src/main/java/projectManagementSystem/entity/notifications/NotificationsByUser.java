package projectManagementSystem.entity.notifications;


import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.User;

import javax.persistence.*;
import java.util.EnumMap;
import java.util.HashMap;

@Entity
public class NotificationsByUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "notifications_types_mapping",
            joinColumns = {@JoinColumn(name = "type", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "is_active", referencedColumnName = "id")})
    @MapKey(name = "notificationsType")
    @Enumerated(EnumType.STRING)

    EnumMap<NotificationType, Boolean> notificationsType = new EnumMap<>(NotificationType.class);
    HashMap<NotificationVia, Boolean> notificationVia = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
// f05c3e11efc6f35ca3bb56f2c73c8ff2d2cad03d

    NotificationsByUser(){
        for (NotificationType value : NotificationType.values()) {
            notificationsType.put(value,false);
        }

//        for (NotificationVia value : NotificationVia.values()) {
//            notificationVia.put(value,false);
//        }

    }
}
