package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Role;

public class RoleRequest {
    private long boardId;
    private long userId;
    private Role role;

    public RoleRequest() {
    }

    public RoleRequest(long boardId, long userId, Role role) {
        this.boardId = boardId;
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
}
