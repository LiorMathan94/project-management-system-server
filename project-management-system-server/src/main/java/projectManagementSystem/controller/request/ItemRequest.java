package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Importance;

import java.time.LocalDate;

public class ItemRequest {
    private Long itemId;
    private Long userId;
    private String status;
    private String type;
    private Long parentId;
    private Long creatorId;
    private Long assignedToId;
    private LocalDate dueDate;
    private Importance importance;
    private String title;
    private String description;

    public ItemRequest() {
    }

    public ItemRequest(Long itemId, Long userId, String status, String type, Long parentId, Long creatorId, Long assignedToId, LocalDate dueDate, Importance importance, String title, String description) {
        this.itemId = itemId;
        this.userId = userId;
        this.status = status;
        this.type = type;
        this.parentId = parentId;
        this.creatorId = creatorId;
        this.assignedToId = assignedToId;
        this.dueDate = dueDate;
        this.importance = importance;
        this.title = title;
        this.description = description;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Importance getImportance() {
        return importance;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
