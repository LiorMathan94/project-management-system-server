package projectManagementSystem.controller.request;

import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.Importance;

import java.time.LocalDate;
import java.util.Date;

public class ItemRequest {
    private long itemId;
    private BoardAction boardAction;
    private long boardId;
    private String status;
    private String type;
    private long parentId;
    private long creatorId;
    private long assignedToId;
    private LocalDate dueDate;
    private Importance importance;
    private String title;
    private String description;

    public ItemRequest() {
    }

    public ItemRequest(String status, String type, Long parentId, Long creatorId, Long assignedToId, LocalDate dueDate,
                       Importance importance, String title, String description) {
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

    public long getItemId() {
        return itemId;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public long getParentId() {
        return parentId;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public long getAssignedToId() {
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

    public BoardAction getBoardAction() {
        return boardAction;
    }

    public void setBoardAction(BoardAction boardAction) {
        this.boardAction = boardAction;
    }

    public void setType(String type) {
        this.type = type;
    }
}