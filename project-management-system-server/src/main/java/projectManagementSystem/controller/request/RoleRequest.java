package projectManagementSystem.controller.request;

import projectManagementSystem.entity.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class RoleRequest {
    private long boardId;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleRequest() {
    }

    public RoleRequest(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public long getBoardId() {
        return boardId;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}
