package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.Item;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoardDTO {
    private long id;
    private String title;
    private Set<String> statuses;
    private Set<String> types;
    private Map<String, List<Item>> items;

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.statuses = board.getStatuses();
        this.types = board.getTypes();
        this.items = board.getItemsByStatus();
    }
}
