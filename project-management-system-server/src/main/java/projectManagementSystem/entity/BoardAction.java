package projectManagementSystem.entity;

public enum BoardAction {
    CREATE_ITEM(Role.LEADER),
    DELETE_ITEM(Role.ADMIN),
    ASSIGN_ITEM(Role.LEADER),
    UPDATE_ITEM(Role.ADMIN),
    CHANGE_ITEM_STATUS(Role.USER),
    ADD_STATUS(Role.ADMIN),
    REMOVE_STATUS(Role.ADMIN),
    ADD_TYPE(Role.ADMIN),
    REMOVE_TYPE(Role.ADMIN),
    ADD_COMMENT(Role.USER),
    DELETE_BOARD(Role.ADMIN);

    private Role role;

    BoardAction(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
