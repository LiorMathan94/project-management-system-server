package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Importance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilterRequest {
    private List<Long> assignedToId;
    private LocalDate dueDate;
    private List<String> status;
    private List<String> type;
    private List<Importance> importance;

    public FilterRequest(){
        assignedToId = new ArrayList<>();
        status = new ArrayList<>();
        type = new ArrayList<>();
        importance = new ArrayList<>();
    }

    public List<Long> getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(List<Long> assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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





