package projectManagementSystem.entity.DTO;

import projectManagementSystem.controller.response.NotificationResponse;
import projectManagementSystem.entity.AuthorizedUser;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoardDTO {
    private long id;
    private String title;
    private Set<String> statuses;
    private Set<String> types;
    private Map<String, List<Item>> items;
    private List<NotificationResponse> notifications;
    private List<AuthorizedUserDTO> authorizedUsers;

    /**
     * Constructor for BoardDTO
     *
     * @param board
     */
    private BoardDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.statuses = board.getStatuses();
        this.types = board.getTypes();
        this.items = board.getItemsByStatus();
        this.notifications = new ArrayList<>();
        this.authorizedUsers = initDTOAuthorizedUsers(board.getAuthorizedUsers());
    }

    /**
     * Creates and returns BoardDTO object from given Board object.
     *
     * @param board - the Board object from which the BoardDTO is created
     * @return BoardDTO object, contains board data that can be revealed to client if board is not null, otherwise - returns null.
     */
    public static BoardDTO createFromBoard(Board board) {
        if (board == null) {
            return null;
        }

        return new BoardDTO(board);
    }

    /**
     * Initialize authorizedUsers data member.
     *
     * @param authorizedUsers
     * @return List of AuthorizedUserDTO
     */
    private List<AuthorizedUserDTO> initDTOAuthorizedUsers(List<AuthorizedUser> authorizedUsers) {
        List<AuthorizedUserDTO> authUsersDTO = new ArrayList<>();
        for (AuthorizedUser authUser : authorizedUsers) {
            authUsersDTO.add(AuthorizedUserDTO.createFromAuthorizedUser(authUser));
        }

        return authUsersDTO;
    }

    public boolean findUserInAuthorizedUsers(User user) {
        for (int i = 0; i < this.authorizedUsers.size(); i++) {
            if (authorizedUsers.get(i).getEmail() == user.getEmail()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return board's ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets board's ID
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return board's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return board's statuses
     */
    public Set<String> getStatuses() {
        return statuses;
    }

    /**
     * @return board's types
     */
    public Set<String> getTypes() {
        return types;
    }

    /**
     * @return board's items, grouped by their status
     */
    public Map<String, List<Item>> getItems() {
        return items;
    }

    /**
     * Sets board's items.
     *
     * @param items
     */
    public void setItems(Map<String, List<Item>> items) {
        this.items = items;
    }

    /**
     * @return board's notifications
     */
    public List<NotificationResponse> getNotifications() {
        return notifications;
    }

    /**
     * Sets board's notifications.
     *
     * @param notifications
     */
    public void setNotifications(List<NotificationResponse> notifications) {
        this.notifications = notifications;
    }

    /**
     * Add a notification to notifications list.
     *
     * @param notification
     */
    public void addNotification(NotificationResponse notification) {
        this.notifications.add(notification);
    }

    /**
     * @return board's authorized users
     */
    public List<AuthorizedUserDTO> getAuthorizedUsers() {
        return authorizedUsers;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", statuses=" + statuses +
                ", types=" + types +
                ", items=" + items +
                ", notifications=" + notifications +
                ", authorizedUsers=" + authorizedUsers +
                '}';
    }
}
