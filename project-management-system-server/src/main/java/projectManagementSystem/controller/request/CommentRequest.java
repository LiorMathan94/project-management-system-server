package projectManagementSystem.controller.request;

public class CommentRequest {
    private long userId;
    private long itemId;
    private String content;

    public CommentRequest() {
    }

    public CommentRequest(long userId, long itemId, String content) {
        this.userId = userId;
        this.itemId = itemId;
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public long getItemId() {
        return itemId;
    }

    public String getContent() {
        return content;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
