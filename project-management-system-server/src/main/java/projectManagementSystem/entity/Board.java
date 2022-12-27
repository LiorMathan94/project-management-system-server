package projectManagementSystem.entity;

import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @ElementCollection
    private Set<String> statuses;
    @ElementCollection
    private Set<String> types;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorizedUser> authorizedUsers;

    public Board() {
    }

    /**
     * Constructor for Board class.
     * @param title
     * @param statuses
     * @param types
     */
    public Board(String title, Set<String> statuses, Set<String> types) {
        this.title = title;
        this.statuses = statuses;
        this.types = types;
        this.items = new ArrayList<>();
        this.authorizedUsers = new ArrayList<>();
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
     * @return board's items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets board's title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets board's items
     * @param items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Adds status to board's statuses
     * @param status
     */
    public void addStatus(String status) {
        this.statuses.add(status);
    }

    /**
     * Removes status from board's statuses
     * @param status
     */
    public void removeStatus(String status) {
        this.items.removeIf(item -> item.getStatus().equals(status));
        this.statuses.remove(status);
    }

    /**
     * Adds type to board's types
     * @param type
     */
    public void addType(String type) {
        this.types.add(type);
    }

    /**
     * Removes type from board's types
     * @param type
     */
    public void removeType(String type) {
        for (Item item : this.items) {
            if (item.getType().equals(type)) {
                item.setType(null);
            }
        }

        this.types.remove(type);
    }

    /**
     * Adds item to board's items
     * @param item
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Updates item if already exists in board's items
     * @param item
     */
    public void updateItem(Item item) {
        Item existingItem = findItemById(item.getId());

        if (existingItem != null) {
            removeItem(existingItem);
            addItem(item);
        }
    }

    /**
     * Removes item from board's items
     * @param item
     */
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    /**
     * @param itemId
     * @return Optional of Item corresponds to itemId if it exists in board's items
     */
    public Optional<Item> getItemById(long itemId) {
        return this.items.stream().filter(item -> item.getId() == itemId).findAny();
    }

    public Map<String, List<Item>> getItemsByStatus() {
        Map<String, List<Item>> itemsByStatus = this.items.stream().collect(groupingBy(Item::getStatus));

        for (String status : this.statuses) {
            itemsByStatus.computeIfAbsent(status, k -> new ArrayList<>());
        }

        return itemsByStatus;
    }

    /**
     * @return board's authorized users
     */
    public List<AuthorizedUser> getAuthorizedUsers() {
        return authorizedUsers;
    }

    /**
     * @return all board's authorized users, grouped by their role
     */
    public Map<Role, List<AuthorizedUser>> getAuthorizedUsersByRole() {
        Map<Role, List<AuthorizedUser>> authorizedUsersByRole = this.authorizedUsers.stream().collect(groupingBy(AuthorizedUser::getRole));

        for (Role role : Role.values()) {
            authorizedUsersByRole.computeIfAbsent(role, k -> new ArrayList<>());
        }

        return authorizedUsersByRole;
    }

    /**
     * Adds user to board's authorized users with the given role
     * @param user
     * @param role
     */
    public void assignUser(User user, Role role) {
        Optional<AuthorizedUser> authorized = getAuthorizedById(user.getId());

        if (authorized.isPresent()) {
            authorized.get().setRole(role);
        } else {
            this.authorizedUsers.add(new AuthorizedUser(user, role));
        }
    }

    /**
     * @param userId
     * @return Optional of AuthorizedUser corresponds to userId.
     */
    public Optional<AuthorizedUser> getAuthorizedById(long userId) {
        for (AuthorizedUser user : this.authorizedUsers) {
            if (user.getUser().getId() == userId) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    /**
     * Removes authorizedUser from board's authorized users
     * @param authorizedUser
     */
    public void removeAuthorizedUser(AuthorizedUser authorizedUser) {
        this.authorizedUsers.remove(authorizedUser);
    }

    /**
     * @param requiredId
     * @return board's item corresponds to requiredId
     */
    private Item findItemById(long requiredId) {
        return this.items.stream().filter(item -> item.getId() == (requiredId)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", statuses=" + statuses +
                ", types=" + types +
                ", items=" + items +
                '}';
    }
}
