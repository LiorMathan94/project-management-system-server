package projectManagementSystem.controller.request;

import java.util.Set;

public class BoardRequest {
    private String title;
    private Set<String> statuses;
    private Set<String> types;
    private Long userId;

    public BoardRequest() {
    }

    public BoardRequest(String title, Set<String> statuses, Set<String> types, Long userId) {
        this.title = title;
        this.statuses = statuses;
        this.types = types;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getStatuses() {
        return statuses;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
