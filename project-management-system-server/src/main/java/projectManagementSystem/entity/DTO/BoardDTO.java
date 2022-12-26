package projectManagementSystem.entity.DTO;

import projectManagementSystem.controller.response.NotificationResponse;
import projectManagementSystem.entity.AuthorizedUser;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.Item;

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
     * @param board
     */
    public BoardDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.statuses = board.getStatuses();
        this.types = board.getTypes();
        this.items = board.getItemsByStatus();
        this.notifications = new ArrayList<>();
        this.authorizedUsers = initDTOAuthorizedUsers(board.getAuthorizedUsers());
    }

    /**
     * Initialize authorizedUsers data member.
     * @param authorizedUsers
     * @return List of AuthorizedUserDTO
     */
    private List<AuthorizedUserDTO> initDTOAuthorizedUsers(List<AuthorizedUser> authorizedUsers) {
        List<AuthorizedUserDTO> authUsersDTO = new ArrayList<>();
        for (AuthorizedUser authUser : authorizedUsers) {
            authUsersDTO.add(new AuthorizedUserDTO(authUser));
        }

        return authUsersDTO;
    }

    /**
     * @return board's ID
     */
    public long getId() {
        return id;
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
     * @param notifications
     */
    public void setNotifications(List<NotificationResponse> notifications) {
        this.notifications = notifications;
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
