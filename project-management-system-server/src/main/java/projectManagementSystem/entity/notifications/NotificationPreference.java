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

    public NotificationPreference() {
    }

    /**
     * Constructor for notification preferences.
     * @param user
     */
    public NotificationPreference(User user) {
        this.boardActions = new ArrayList<>();
        this.notificationViaList = new ArrayList<>();
        this.user = user;
    }

    /**
     * @return notification's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @return notification's boards action list, to notify for
     */
    public List<BoardAction> getNotifyOnEvents() {
        return boardActions;
    }

    /**
     * @return notification's notify via list
     */
    public List<NotificationVia> getNotificationViaList() {
        return notificationViaList;
    }

    /**
     * Sets notification's board actions.
     * @param boardActions
     */
    public void setBoardActions(List<BoardAction> boardActions) {
        this.boardActions = boardActions;
    }

    /**
     * Sets notification's via list.
     * @param notificationViaList
     */
    public void setNotificationViaList(List<NotificationVia> notificationViaList) {
        this.notificationViaList = notificationViaList;
    }

    /**
     * Adds notification event (board action) to notify for.
     * @param boardActions
     */
    public void addNotificationEvent(BoardAction boardActions) {
        this.boardActions.add(boardActions);
    }

    /**
     * Adds notification method.
     * @param notificationVia
     */
    public void addNotificationVia(NotificationVia notificationVia) {
        this.notificationViaList.add(notificationVia);
    }
}
