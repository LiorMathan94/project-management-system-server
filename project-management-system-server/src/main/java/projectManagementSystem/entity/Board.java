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

    public Board(String title, Set<String> statuses, Set<String> types) {
        this.title = title;
        this.statuses = statuses;
        this.types = types;
        this.items = new ArrayList<>();
        this.authorizedUsers = new ArrayList<>();
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

    public List<Item> getItems() {
        return items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addStatus(String status) {
        this.statuses.add(status);
    }

    public void removeStatus(String status) {
        for (int i = 0; i < this.items.size(); i++) {
            if (items.get(i).getStatus().equals(status)) {
                this.items.remove(items.get(i));
            }
        }

        this.statuses.remove(status);
    }

    public void addType(String type) {
        this.types.add(type);
    }

    public void removeType(String type) {
        for (Item item : this.items) {
            if (item.getType() != null && item.getType().equals(type)) {
                item.setType(null);
            }
        }

        this.types.remove(type);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void updateItem(Item item) {
        Item existingItem = findItemById(item.getId());

        if (existingItem != null) {
            removeItem(existingItem);
            addItem(item);
        }
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

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

    public List<AuthorizedUser> getAuthorizedUsers() {
        return authorizedUsers;
    }

    public Map<Role, List<AuthorizedUser>> getAuthorizedUsersByRole() {
        Map<Role, List<AuthorizedUser>> authorizedUsersByRole = this.authorizedUsers.stream().collect(groupingBy(AuthorizedUser::getRole));

        for (Role role : Role.values()) {
            authorizedUsersByRole.computeIfAbsent(role, k -> new ArrayList<>());
        }

        return authorizedUsersByRole;
    }

    public void assignUser(User user, Role role) {
        Optional<AuthorizedUser> authorized = getAuthorizedById(user.getId());

        if (authorized.isPresent()) {
            authorized.get().setRole(role);
        } else {
            this.authorizedUsers.add(new AuthorizedUser(user, role));
        }
    }

    public Optional<AuthorizedUser> getAuthorizedById(long userId) {
        for (AuthorizedUser user : this.authorizedUsers) {
            if (user.getUser().getId() == userId) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public void removeAuthorizedUser(AuthorizedUser authorizedUser) {
        this.authorizedUsers.remove(authorizedUser);
    }

    private Item findItemById(long requiredId) {
        return this.items.stream().filter(item -> item.getId() == (requiredId)).findFirst().orElse(null);
    }
}
