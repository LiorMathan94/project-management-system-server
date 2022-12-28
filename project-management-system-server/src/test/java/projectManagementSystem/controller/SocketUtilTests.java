package projectManagementSystem.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SocketUtilTests {
    @Mock
    private SimpMessagingTemplate templateMock;

    @InjectMocks
    private SocketUtil socketUtil;

    @Test
    public void testUpdateBoard() {
        BoardDTO board = new BoardDTO(new Board());
        board.setId(1L);
        board.setNotifications(new ArrayList<>());
        BoardDTO resultBoardDto = socketUtil.updateBoard(board);

        verify(templateMock).convertAndSend("/topic/updates-1", board);
        verify(templateMock).convertAndSend("/topic/notifications-1", "Test notifications");

        assertEquals(board, resultBoardDto,"");
    }
}
