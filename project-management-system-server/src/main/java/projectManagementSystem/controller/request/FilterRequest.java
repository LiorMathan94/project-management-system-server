package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Importance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterRequest {
    private List<String> assignedToUser;
    private LocalDate dueDate;
    private List<String> status;
    private List<String> type;
    private List<Importance> importance;

    public FilterRequest(){
        assignedToUser = new ArrayList<>();
        status = new ArrayList<>();
        type = new ArrayList<>();
        importance = new ArrayList<>();
    }

    public FilterRequest(List<String> assignedToUser, LocalDate dueDate, List<String> status, List<String> type, List<Importance> importance) {
        this.assignedToUser = assignedToUser;
        this.dueDate = dueDate;
        this.status = status;
        this.type = type;
        this.importance = importance;
    }

    public List<String> getassignedToUser() {
        return assignedToUser;
    }

    public void setassignedToUser(List<String> assignedToUser) {
        this.assignedToUser = assignedToUser;
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





