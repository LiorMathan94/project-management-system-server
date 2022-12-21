package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Role;

public class RoleRequest {
    private long boardId;
    private String emailOfAssignedUser;
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
