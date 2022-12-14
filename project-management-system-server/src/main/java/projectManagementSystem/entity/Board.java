package projectManagementSystem.entity;

import projectManagementSystem.utils.InputValidation;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @ElementCollection
    private List<String> statuses;
    @ElementCollection
    private List<String> types;

    public Board() {
    }

    public Board(String title, List<String> statuses, List<String> types) {
        this.title = title;
        this.statuses = statuses;
        this.types = types;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addStatus(String status) {
        if (!InputValidation.isValidItemLabel(status)) {
            throw new IllegalArgumentException(String.format("Bad status: %s. A status can contain 1-20 characters.", status));
        }

        this.statuses.add(status);
    }

    public void removeStatus(String status) {
        this.statuses.remove(status);
    }

    public void addType(String type) {
        if (!InputValidation.isValidItemLabel(type)) {
            throw new IllegalArgumentException(String.format("Bad type: %s. A type can contain 1-20 characters.", type));
        }

        this.types.add(type);
    }

    public void removeType(String type) {
        this.types.remove(type);
    }
}
