package projectManagementSystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;
import projectManagementSystem.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTests {
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BoardService boardService;
    private Board validBoard;
    private BoardRequest validBoardRequest;

    @BeforeEach
    public void setup() {
        String title = "board title";

        Set<String> statuses = new HashSet<>();
        statuses.add("to do");
        statuses.add("done");

        Set<String> types = new HashSet<>();
        types.add("task");
        types.add("bug");

        validBoardRequest = new BoardRequest(title, statuses, types);
        validBoard = new Board(title, statuses, types);
    }

    @Test
    public void createBoard_ValidBoardRequest_ReturnsBoardDTO() {
        when(boardRepository.save(any(Board.class))).thenReturn(validBoard);
        BoardDTO result = boardService.createBoard(validBoardRequest);

        assertEquals(validBoardRequest.getTitle(), result.getTitle());
        assertEquals(validBoardRequest.getStatuses(), result.getStatuses());
        assertEquals(validBoardRequest.getTypes(), result.getTypes());
    }

    @Test
    public void createBoard_NullBoardRequest_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            boardService.createBoard(null);
        });
    }

    @Test
    public void setTitle_ValidParams_ReturnsBoardDTO() {
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(validBoard));
        when(boardRepository.save(any(Board.class))).thenReturn(validBoard);
        BoardDTO result = boardService.setTitle(0L, "test title");

        assertEquals("test title", result.getTitle());
    }

    @Test
    public void removeStatus_NonExistingStatus_ThrowsNullPointerException() {
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(validBoard));

        assertThrows(NullPointerException.class, () -> {
            boardService.removeStatus(0L, "non existing status");
        });
    }

    @Test
    public void addItem_NullItem_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            boardService.addItem(null);
        });
    }
}
