package projectManagementSystem.entity;

import java.time.LocalDate;
import java.util.List;

public class Item {
    private int id;
    private String status;
    private String type;
    private Item parent;
    private int creatorId;
    private int assignedToId;
    private LocalDate dueDate;
    private Importance importance;
    private String title;
    private String Description;
    private List<Comment> commentList;


    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Item getParent() {
        return parent;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public int getAssignedToId() {
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
        return Description;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public void setAssignedToId(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
