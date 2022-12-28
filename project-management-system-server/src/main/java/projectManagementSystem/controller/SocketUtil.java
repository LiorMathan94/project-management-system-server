package projectManagementSystem.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import projectManagementSystem.entity.DTO.BoardDTO;

@Component
public class SocketUtil {
    private SimpMessagingTemplate template;

    /**
     * Constructor for SocketUtil.
     * @param template
     */
    public SocketUtil(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * Sends board to all subscribers using socket.
     * @param board
     * @return board
     *
     * @Throws NullPointerException if board is null
     */
    public BoardDTO updateBoard(BoardDTO board) {
        if(board==null){
            throw new NullPointerException("updateBoard() method in SocketUtil received BoardDTO null");
        }
        String destination = "/topic/updates-" + board.getId();
        template.convertAndSend(destination, board);
        notify(board);
        return board;
    }

    /**
     * Sends board's notifications to all subscribers using socket.
     * @param board
     */
    private void notify(BoardDTO board) {
        if(board==null){
            throw new NullPointerException("notify() method in SocketUtil received BoardDTO null");
        }
        String destination = "/topic/notifications-" + board.getId();
        template.convertAndSend(destination, board.getNotifications());
    }
}