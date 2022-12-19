package projectManagementSystem.controller.request;

import java.util.Set;

public class BoardRequest {
    private String title;
    private Set<String> statuses;
    private Set<String> types;

    public BoardRequest() {
    }

    public BoardRequest(String title, Set<String> statuses, Set<String> types) {
        this.title = title;
        this.statuses = statuses;
        this.types = types;
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
}
