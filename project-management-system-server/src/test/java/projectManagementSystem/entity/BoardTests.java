package projectManagementSystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {
    private Board board;

    @BeforeEach
    void beforeEach() {
        Set<String> statuses = new HashSet<>();
        statuses.add("To Do");
        statuses.add("In Progress");
        statuses.add("Done");

        Set<String> types = new HashSet<>();
        types.add("Task");
        types.add("Bug");

        board = new Board("Test Board", statuses, types);
    }

    @Test
    public void addItem_ItemAddedToBoard() {
        Item item = new Item.ItemBuilder(0L, 0L, "Test Item").setType("Task").setStatus("To Do").build();

        board.addItem(item);

        assertTrue(board.getItems().contains(item));
    }

    @Test
    public void removeStatus_StatusAndItsItemsRemovedFromBoard() {
        Item item = new Item.ItemBuilder(0L, 0L, "Test Item").setType("Task").setStatus("To Do").build();
        board.addItem(item);

        board.removeStatus("To Do");

        assertFalse(board.getStatuses().contains("To Do"));
        assertFalse(board.getItems().contains(item));
    }

    @Test
    public void addStatus_StatusIsAddedToBoard() {
        board.addStatus("Testing");
        assertTrue(board.getStatuses().contains("Testing"));
    }

    @Test
    public void addType_TypeIsAddedToBoard() {
        board.addType("Feature");
        assertTrue(board.getTypes().contains("Feature"));
    }

    @Test
    void removeType_TypeIsRemovedFromBoard() {
        Item item = new Item.ItemBuilder(0L, 0L, "Test Item").setType("Bug").setStatus("To Do").build();
        board.addItem(item);

        board.removeType("Bug");
        assertFalse(board.getTypes().contains("Bug"));
        assertEquals(null, item.getType());
    }
}
