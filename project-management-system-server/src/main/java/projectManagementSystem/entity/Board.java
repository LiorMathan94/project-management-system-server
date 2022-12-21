package projectManagementSystem.entity;

import projectManagementSystem.utils.InputValidation;

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;

    public Board() {
    }

    public Board(String title, Set<String> statuses, Set<String> types) {
        this.title = title;
        this.statuses = statuses;
        this.types = types;
        this.items = new ArrayList<Item>();
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
        if (!InputValidation.isValidLabel(status)) {
            throw new IllegalArgumentException(String.format("Bad status: %s. A status can contain 1-20 characters.", status));
        }

        this.statuses.add(status);
    }

    public void removeStatus(String status) {
        this.statuses.remove(status);
    }

    public void addType(String type) {
        if (!InputValidation.isValidLabel(type)) {
            throw new IllegalArgumentException(String.format("Bad type: %s. A type can contain 1-20 characters.", type));
        }

        this.types.add(type);
    }

    public void removeType(String type) {
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

    private Item findItemById(long requiredId) {
        return this.items.stream().filter(item -> item.getId() == (requiredId)).findFirst().orElse(null);
    }
}
