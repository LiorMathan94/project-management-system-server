package projectManagementSystem.entity;

public enum BoardAction {
    SET_TITLE(Role.ADMIN, "title", "Board's title was set"),
    ADD_STATUS(Role.ADMIN, "addStatus", "A status was added to the board"),
    REMOVE_STATUS(Role.ADMIN, "removeStatus", "A status was removed from the board"),
    ADD_TYPE(Role.ADMIN, "addType", "A type was added to the board"),
    REMOVE_TYPE(Role.ADMIN, "removeType", "A type was removed from the board"),

    CREATE_ITEM(Role.LEADER, "addItem", "Item was created"),
    ASSIGN_ITEM(Role.LEADER, "assignItem", "Item was assigned to a user"),
    SET_ITEM_DUE_DATE(Role.ADMIN, "setItemDueDate", "Item's due date was updated"),
    SET_ITEM_STATUS(Role.USER, "setItemStatus", "Item's status was updated"),
    SET_ITEM_IMPORTANCE(Role.ADMIN, "setItemImportance", "Item's importance was updated"),
    SET_ITEM_TITLE(Role.LEADER, "setItemTitle", "Item's title was updated"),
    SET_ITEM_DESCRIPTION(Role.LEADER, "setItemDescription", "Item's description was updated"),
    SET_ITEM_PARENT(Role.LEADER, "setItemParent", "Item's parent was updated"),
    SET_ITEM_TYPE(Role.LEADER, "setItemType", "Item's type was updated"),
    ADD_COMMENT(Role.USER, "addComment", "A comment was added to a the board's item"),
    DELETE_ITEM(Role.ADMIN, "removeItem", "Item was deleted"),

    GRANT_USER_ROLE(Role.ADMIN, "grantUserRole", "User permission was granted"),
    DELETE_BOARD(Role.ADMIN, "delete", "Board was deleted"),

    FILTER(Role.USER, "filter", "board's items were filtered"),
    UNKNOWN(Role.ADMIN, null, null);

    private Role role;
    private String route;
    private String description;

    BoardAction(Role role, String route, String description) {
        this.role = role;
        this.route = route;
        this.description = description;
    }

    public Role getRole() {
        return role;
    }

    public String getRoute() {
        return route;
    }

    public String getDescription() {
        return description;
    }

    public static BoardAction getByRoute(String route) {
        for (BoardAction action : values()) {
            if (action.getRoute() != null && route.contains(action.getRoute())) {
                return action;
            }
        }

        return UNKNOWN;
    }
}
