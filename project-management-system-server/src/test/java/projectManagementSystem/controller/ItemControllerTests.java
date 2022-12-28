package projectManagementSystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.CommentRequest;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.DTO.ItemDTO;
import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;
import projectManagementSystem.service.BoardService;
import projectManagementSystem.service.ItemService;
import projectManagementSystem.service.NotificationService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

        boardDTOSuccess = BoardDTO.createFromBoard(new Board(title, statuses, types));

        reset(itemService);
        reset(boardService);
        reset(notificationService);
        reset(socketUtil);
    }

    @Test
    void create_ValidItemRequest_ResponseEntityOk() {
        given(itemService.createItem(any(validItemRequest.getClass()))).willReturn(validItem);
        given(boardService.addItem(any(validItem.getClass()))).willReturn(boardDTOSuccess);
        given(boardService.hasStatus(0L, validItemRequest.getStatus())).willReturn(true);
        given(boardService.hasType(0L, validItemRequest.getType())).willReturn(true);
        assertEquals(200, itemController.create(boardDTOSuccess.getId(), validItemRequest).getStatusCodeValue(), "Create with valid ItemRequest should return ResponseEntity status 200 (OK)");
    }

    @Test
    void create_NullRequest_ResponseEntityBadRequest() {
        assertEquals(400, itemController.create(boardDTOSuccess.getId(), null).getStatusCodeValue(), "create() with invalid ItemRequest should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void update_validItemRequest_ResponseEntityOk() {
        given(itemService.updateItem(any(validItemRequest.getClass()))).willReturn(null);
        given(boardService.getBoardById(anyLong())).willReturn(boardDTOSuccess);
        given(boardService.hasStatus(0L, validItemRequest.getStatus())).willReturn(true);
        given(boardService.hasType(0L, validItemRequest.getType())).willReturn(true);
        assertEquals(200, itemController.update(boardDTOSuccess.getId(), BoardAction.UNKNOWN, validItemRequest).getStatusCodeValue(), "update() with valid ItemRequest should return ResponseEntity status 200 (OK)");
    }

    @Test
    public void addComment_ValidCommentRequest_ResponseEntityOk() {
        CommentRequest commentRequest = new CommentRequest(1L, 1L, "test comment");
        given(itemService.addComment(1L, 1L, commentRequest.getContent())).willReturn(ItemDTO.createFromItem(validItem));
        given(boardService.getBoardById(any(Long.class))).willReturn(boardDTOSuccess);

        assertEquals(200, itemController.addComment(1, commentRequest, 1).getStatusCodeValue());
    }

    @Test
    public void delete_ValidRequest_ResponseEntityOk() {
        doReturn(boardDTOSuccess).when(itemService).delete(1);
        doNothing().when(notificationService).notifyAll(any(), any());

        assertEquals(200, itemController.delete(1).getStatusCodeValue());
        verify(itemService).delete(1);
        verify(notificationService).notifyAll(any(), any());
        verify(socketUtil).updateBoard(any());
    }

    @Test
    public void delete_InvalidRequest_ResponseEntityBadRequest() {
        doThrow(new IllegalArgumentException("Invalid item id")).when(itemService).delete(1);

        assertEquals(400, itemController.delete(1).getStatusCodeValue());
        verify(itemService).delete(1);
    }
}