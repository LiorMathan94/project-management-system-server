package projectManagementSystem.controller.request;

import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.Importance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilterRequest {

    private List<Long> creatorId;
    private List<Long> assignedToId;
    private List<LocalDate> dueDate;
    private List<Long> parentId;
    private List<String> status;
    private List<String> type;
    private List<Importance> importance;

    public FilterRequest(){
        creatorId = new ArrayList<>();
        assignedToId = new ArrayList<>();
        dueDate = new ArrayList<>();
        parentId = new ArrayList<>();
        status = new ArrayList<>();
        type = new ArrayList<>();
        importance = new ArrayList<>();
    }


    public List<Long> getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(List<Long> creatorId) {
        this.creatorId = creatorId;
    }

    public List<Long> getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(List<Long> assignedToId) {
        this.assignedToId = assignedToId;
    }

    public List<LocalDate> getDueDate() {
        return dueDate;
    }

    public void setDueDate(List<LocalDate> dueDate) {
        this.dueDate = dueDate;
    }

    public List<Long> getParentId() {
        return parentId;
    }

    public void setParentId(List<Long> parentId) {
        this.parentId = parentId;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<Importance> getImportance() {
        return importance;
    }

    public void setImportance(List<Importance> importance) {
        this.importance = importance;
    }
}





