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

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.statuses = board.getStatuses();
        this.types = board.getTypes();
        this.items = board.getItemsByStatus();
        this.notifications = new ArrayList<>();
        this.authorizedUsers = initDtoAuthorizedUsers(board.getAuthorizedUsers());
    }

    private List<AuthorizedUserDTO> initDtoAuthorizedUsers(List<AuthorizedUser> authorizedUsers) {
        List<AuthorizedUserDTO> authUsersDto = new ArrayList<>();
        for (AuthorizedUser authUser : authorizedUsers) {
            authUsersDto.add(new AuthorizedUserDTO(authUser));
        }
        return authUsersDto;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getStatuses() {
        return statuses;
    }

    public Set<String> getTypes() {
        return types;
    }

    public Map<String, List<Item>> getItems() {
        return items;
    }

    public void setItems(Map<String, List<Item>> items) {
        this.items = items;
    }

    public List<NotificationResponse> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationResponse> notifications) {
        this.notifications = notifications;
    }

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
