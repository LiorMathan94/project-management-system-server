package projectManagementSystem.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import projectManagementSystem.entity.DTO.BoardDTO;

@Component
public class SocketUtil {
    private SimpMessagingTemplate template;

    public SocketUtil(SimpMessagingTemplate template) {
        this.template = template;
    }

    public BoardDTO updateBoard(BoardDTO board) {
        String destination = "/topic/updates-" + board.getId();
        template.convertAndSend(destination, board);
        notify(board);
        return board;
    }

    private void notify(BoardDTO board) {
        String destination = "/topic/notifications-" + board.getId();
        template.convertAndSend(destination, board.getNotifications());
    }
}