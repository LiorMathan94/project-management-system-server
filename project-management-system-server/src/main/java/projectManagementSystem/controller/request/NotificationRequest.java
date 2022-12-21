package projectManagementSystem.controller.request;

import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.notifications.NotificationVia;

import java.util.List;

public class NotificationRequest {
    private long userId;
    private List<BoardAction> boardActions;
    private List<NotificationVia> notificationViaList;

    public NotificationRequest() {
    }

    public long getUserId() {
        return userId;
    }

    public List<BoardAction> getBoardActions() {
        return boardActions;
    }

    public List<NotificationVia> getNotificationViaList() {
        return notificationViaList;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setBoardActions(List<BoardAction> boardActions) {
        this.boardActions = boardActions;
    }

    public void setNotificationViaList(List<NotificationVia> notificationViaList) {
        this.notificationViaList = notificationViaList;
    }
}
