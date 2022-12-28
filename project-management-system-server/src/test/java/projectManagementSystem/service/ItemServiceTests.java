package projectManagementSystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.DTO.ItemDTO;
import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private ItemService itemService;
    private ItemRequest validItemRequest;
    private Item validItem;


    @BeforeEach
    public void setup() {
        validItemRequest = new ItemRequest("Doing", "Task", 0L, 0L, 0L,
                LocalDate.now(), Importance.LEVEL1, "item title", "");
        validItem = new Item.ItemBuilder(0L, validItemRequest.getCreatorId(), validItemRequest.getTitle())
                .setStatus(validItemRequest.getStatus())
                .setImportance(validItemRequest.getImportance())
                .setDueDate(validItemRequest.getDueDate())
                .setDescription(validItemRequest.getDescription())
                .setAssignedToId(validItemRequest.getAssignedToId())
                .setType(validItemRequest.getType()).build();
    }

    @Test
    public void createItem_ValidItemRequest_ReturnsItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(validItem);
        Item result = itemService.createItem(validItemRequest);

        assertEquals(validItemRequest.getBoardId(), result.getBoardId());
        assertEquals(validItemRequest.getCreatorId(), result.getCreatorId());
        assertEquals(validItemRequest.getTitle(), result.getTitle());
    }

    @Test
    public void createItem_NullItemRequest_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            itemService.createItem(null);
        });
    }

    @Test
    public void updateItemTitle_ValidItemRequest_ReturnValidItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(validItem);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(validItem));
        validItemRequest.setBoardAction(BoardAction.SET_ITEM_TITLE);
        ItemDTO result = itemService.updateItem(validItemRequest);

        assertEquals(validItemRequest.getTitle(), result.getTitle());
    }

    @Test
    public void updateItemStatus_ValidItemRequest_ReturnValidItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(validItem);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(validItem));
        validItemRequest.setBoardAction(BoardAction.SET_ITEM_STATUS);
        ItemDTO result = itemService.updateItem(validItemRequest);

        assertEquals(validItemRequest.getStatus(), result.getStatus());
    }

    @Test
    public void updateItemType_ValidItemRequest_ReturnValidItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(validItem);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(validItem));
        validItemRequest.setBoardAction(BoardAction.SET_ITEM_TYPE);
        ItemDTO result = itemService.updateItem(validItemRequest);

        assertEquals(validItemRequest.getType(), result.getType());
    }

    @Test
    public void updateItemDueDate_ValidItemRequest_ReturnValidItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(validItem);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(validItem));
        validItemRequest.setBoardAction(BoardAction.SET_ITEM_DUE_DATE);
        ItemDTO result = itemService.updateItem(validItemRequest);

        assertEquals(validItemRequest.getDueDate(), result.getDueDate());
    }

    @Test
    public void updateItemAssignedUser_ValidItemRequest_ReturnValidItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(validItem);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(validItem));
        validItemRequest.setBoardAction(BoardAction.ASSIGN_ITEM);
        ItemDTO result = itemService.updateItem(validItemRequest);

        assertEquals(validItemRequest.getAssignedToId(), result.getAssignedToId());
    }

    @Test
    public void updateItemImportance_ValidItemRequest_ReturnValidItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(validItem);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(validItem));
        validItemRequest.setBoardAction(BoardAction.SET_ITEM_IMPORTANCE);
        ItemDTO result = itemService.updateItem(validItemRequest);

        assertEquals(validItemRequest.getImportance(), result.getImportance());
    }

    @Test
    public void updateItem_NullItemRequest_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            itemService.updateItem(null);
        });
    }
}
