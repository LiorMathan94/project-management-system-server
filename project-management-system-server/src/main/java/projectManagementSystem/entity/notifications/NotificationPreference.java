package projectManagementSystem.entity.notifications;

import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection
    @Column(name = "notify_on_events")
    @Enumerated(EnumType.ORDINAL)
    private List<BoardAction> boardActions;

    @ElementCollection
    @Column(name = "notify_via")
    @Enumerated(EnumType.ORDINAL)
    private List<NotificationVia> notificationViaList;

    @OneToOne(mappedBy = "notificationPreferences")
    private User user;

    public NotificationPreference(User user) {
        this.boardActions = new ArrayList<>();
        this.notificationViaList = new ArrayList<>();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public List<BoardAction> getNotifyOnEvents() {
        return boardActions;
    }

    public List<NotificationVia> getNotificationViaList() {
        return notificationViaList;
    }

    public void setBoardActions(List<BoardAction> boardActions) {
        this.boardActions = boardActions;
    }

    public void setNotificationViaList(List<NotificationVia> notificationViaList) {
        this.notificationViaList = notificationViaList;
    }

    public void addNotificationEvent(BoardAction boardActions) {
        this.boardActions.add(boardActions);
    }

    public void addNotificationVia(NotificationVia notificationVia) {
        this.notificationViaList.add(notificationVia);
    }
}
