package projectManagementSystem.entity;

public enum BoardAction {
    CREATE_BOARD(null, "create", "A board eas created"),
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

    FILTER(Role.USER, "filter", "Board's items were filtered"),

    GET_BOARD(Role.USER, "getBoardById", "Board was returned to a user"),
    GET_USER_BOARDS(null, "getBoardsByUserId", "All user's boards were sent to a user"),
    UNKNOWN(Role.ADMIN, null, null);

    private Role role;
    private String route;
    private String description;

    /**
     * Constructor for BoardAction
     * @param role
     * @param route
     * @param description
     */
    BoardAction(Role role, String route, String description) {
        this.role = role;
        this.route = route;
        this.description = description;
    }

    /**
     * @return instance's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @return instance's route
     */
    public String getRoute() {
        return route;
    }

    /**
     * @return instance's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param route
     * @return board action that matches the route
     */
    public static BoardAction getByRoute(String route) {
        for (BoardAction action : values()) {
            if (action.getRoute() != null && route.contains(action.getRoute())) {
                return action;
            }
        }

        return UNKNOWN;
    }
}
