package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Role;

public class RoleRequest {
    private long boardId;
    private long userId;
    private Role role;

    public RoleRequest() {
    }

    public RoleRequest(long userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    public long getBoardId() {
        return boardId;
    }

    public long getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}
