package projectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        template.convertAndSend("/topic/updates", board);
        return board;
    }
}