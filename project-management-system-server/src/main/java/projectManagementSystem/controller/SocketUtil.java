package projectManagementSystem.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import projectManagementSystem.entity.DTO.BoardDTO;

@Component
public class SocketUtil {
    private final SimpMessagingTemplate template;

    public SocketUtil(SimpMessagingTemplate template) {
        this.template = template;
    }

    public BoardDTO updateBoard(BoardDTO board) {
        template.convertAndSend("/topic/join", board);
        return board;
    }
}