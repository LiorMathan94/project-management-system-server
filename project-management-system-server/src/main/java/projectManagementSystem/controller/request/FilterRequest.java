package projectManagementSystem.controller.request;

import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.Importance;

import java.time.LocalDate;

public class FilterRequest {

    private Long[] creatorId;
    private Long[] assignedToId;
    private LocalDate[] dueDate;
    private Long[] parentId;
    private String[] status;
    private String[] type;
    private Importance[] importance;

    public FilterRequest(Long[] creatorId, Long[] assignedToId, LocalDate[] dueDate, Long[] parentId, String[] status, String[] type, Importance[] importance) {
        this.creatorId = creatorId;
        this.assignedToId = assignedToId;
        this.dueDate = dueDate;
        this.parentId = parentId;
        this.status = status;
        this.type = type;
        this.importance = importance;
    }

    public Long[] getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long[] creatorId) {
        this.creatorId = creatorId;
    }

    public Long[] getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long[] assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LocalDate[] getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate[] dueDate) {
        this.dueDate = dueDate;
    }

    public Long[] getParentId() {
        return parentId;
    }

    public void setParentId(Long[] parentId) {
        this.parentId = parentId;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }

    public Importance[] getImportance() {
        return importance;
    }

    public void setImportance(Importance[] importance) {
        this.importance = importance;
    }


}





