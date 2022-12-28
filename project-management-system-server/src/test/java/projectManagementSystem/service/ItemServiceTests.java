package projectManagementSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.Item;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BoardRepository boardRepository;

    private ItemService itemService;

    @BeforeEach
    public void setup() {
        itemService = new ItemService(itemRepository, boardRepository);
    }

//    @Test
//    public void testCreateItem() {
//        // setup
//        long boardId = 1L;
//        long creatorId = 2L;
//        String title = "Test Title";
//        ItemRequest itemRequest = new ItemRequest();
//        itemRequest.setBoardId(boardId);
//        itemRequest.setCreatorId(creatorId);
//        itemRequest.setTitle(title);
//
//        Item item = new Item();
//        item.setBoardId(boardId);
//        item.setCreatorId(creatorId);
//        item.setTitle(title);
//
//        when(itemRepository.save(item)).thenReturn(item);
//
//        // when
//        Item result = itemService.createItem(itemRequest);
//
//        // then
//        assertEquals(boardId, result.getBoardId());
//        assertEquals(creatorId, result.getCreatorId());
//        assertEquals(title, result.getTitle());
//    }
}
