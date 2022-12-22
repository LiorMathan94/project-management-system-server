package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class RoleRequest {
    private long boardId;
    private String emailOfAssignedUser;
    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleRequest() {
    }

    public RoleRequest(String emailOfAssignedUser, Role role) {
        this.emailOfAssignedUser = emailOfAssignedUser;
        this.role = role;
    }

    public long getBoardId() {
        return boardId;
    }

    public String getEmailOfAssignedUser() {
        return emailOfAssignedUser;
    }

    public Role getRole() {
        return role;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}
