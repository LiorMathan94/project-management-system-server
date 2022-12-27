package projectManagementSystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;
import projectManagementSystem.service.BoardService;
import projectManagementSystem.service.ItemService;
import projectManagementSystem.service.NotificationService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTests {
    @Mock
    private BoardService boardService;
    @Mock
    private ItemService itemService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private SocketUtil socketUtil;
    @InjectMocks
    private ItemController itemController;
    private ItemRequest validItemRequest;
    private BoardDTO boardDTOSuccess;
    private Item validItem;

    @BeforeEach
    void beforeEach() {
        validItemRequest = new ItemRequest("Doing", "Task", 0L, 0L, 0L,
                LocalDate.now(), Importance.LEVEL1, "item title", "");

        validItem = new Item.ItemBuilder(0L, validItemRequest.getCreatorId(), validItemRequest.getTitle()).build();

        String title = "board title";
        Set<String> statuses = new HashSet<>();
        statuses.add("to do");
        statuses.add("done");
        Set<String> types = new HashSet<>();
        types.add("task");
        types.add("bug");

        boardDTOSuccess = new BoardDTO(new Board(title, statuses, types));
    }

    @Test
    void create_validItemRequest_ResponseEntityOk() {
        given(itemService.createItem(validItemRequest)).willReturn(validItem);
        given(boardService.addItem(validItem)).willReturn(boardDTOSuccess);
        given(boardService.hasStatus(0L, validItemRequest.getStatus())).willReturn(true);
        given(boardService.hasType(0L, validItemRequest.getType())).willReturn(true);
        assertEquals(200, itemController.create(boardDTOSuccess.getId(), validItemRequest).getStatusCodeValue(), "Create with valid ItemRequest should return ResponseEntity status 200 (OK)");
    }
}