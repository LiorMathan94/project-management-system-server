package projectManagementSystem.controller.response;

public class NotificationResponse {
    private long userId;
    private String message;

    public NotificationResponse() {
    }

    public NotificationResponse(long userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
