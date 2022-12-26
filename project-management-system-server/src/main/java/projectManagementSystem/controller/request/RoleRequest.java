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

    /**
     * Constuctor for RoleRequest
     * @param email
     * @param role
     */
    public RoleRequest(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    /**
     * @return board's ID
     */
    public long getBoardId() {
        return boardId;
    }

    /**
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return requested role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets boardId
     * @param boardId
     */
    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}
