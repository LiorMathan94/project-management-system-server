package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Importance;

import java.time.LocalDate;

public class ItemRequest {
    private Long itemId;

    private Long boardId;
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

    public ItemRequest(Long boardId, String status, String type, Long parentId, Long creatorId, Long assignedToId, LocalDate dueDate, Importance importance, String title, String description) {
        this.boardId = boardId;
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

    public Long getBoardId() {
        return boardId;
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

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
