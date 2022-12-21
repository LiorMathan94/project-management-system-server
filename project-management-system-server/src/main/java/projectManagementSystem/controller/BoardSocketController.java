package projectManagementSystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.service.BoardService;
import projectManagementSystem.service.ItemService;
import projectManagementSystem.service.UserRoleService;

@Controller
@ComponentScan
public class BoardSocketController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserRoleService userRoleService;
    private final Logger logger = LogManager.getLogger(BoardController.class.getName());

    public BoardSocketController() {
    }

    @MessageMapping("/join")
    @SendTo("/topic/join")
    public BoardDTO join(long boardId) {
        logger.info("in join()");

        try {
            return boardService.join(boardId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
