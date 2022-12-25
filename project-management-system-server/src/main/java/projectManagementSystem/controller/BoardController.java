package projectManagementSystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.controller.request.CommentRequest;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.controller.request.RoleRequest;
import projectManagementSystem.controller.response.NotificationResponse;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Role;
import projectManagementSystem.entity.AuthorizedUser;
import projectManagementSystem.service.BoardService;
import projectManagementSystem.service.ItemService;
import projectManagementSystem.service.NotificationService;
import projectManagementSystem.service.UserRoleService;
import projectManagementSystem.utils.InputValidation;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final ItemService itemService;
    private final UserRoleService userRoleService;
    private final NotificationService notificationService;
    private final SocketUtil socketUtil;

    private static final Logger logger = LogManager.getLogger(BoardController.class.getName());

    @Autowired
    public BoardController(BoardService boardService, UserRoleService userRoleService, ItemService itemService,
                           NotificationService notificationService, SocketUtil socketUtil) {
        this.boardService = boardService;
        this.itemService = itemService;
        this.notificationService = notificationService;
        this.userRoleService = userRoleService;
        this.socketUtil = socketUtil;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ResponseEntity<Response<BoardDTO>> create(@RequestAttribute long userId, @RequestBody BoardRequest boardRequest) {
        logger.info("in BoardController.create()");

        try {
            BoardDTO board = boardService.createBoard(boardRequest);
            this.userRoleService.add(board.getId(), userId, Role.ADMIN);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/title")
    public ResponseEntity<Response<BoardDTO>> setTitle(@RequestHeader long boardId, @RequestParam String value) {
        logger.info("in BoardController.setTitle()");

        if (!InputValidation.isValidLabel(value)) {
            return ResponseEntity.badRequest().body(Response.failure("Invalid title: " + value));
        }

        try {
            BoardDTO board = boardService.setTitle(boardId, value);
            board.setNotifications(notifyBoardUsers(board.getId(), BoardAction.SET_TITLE));
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/addStatus")
    public ResponseEntity<Response<BoardDTO>> addStatus(@RequestHeader long boardId, @RequestParam String value) {
        logger.info("in BoardController.addStatus()");

        if (!InputValidation.isValidLabel(value)) {
            return ResponseEntity.badRequest().body(Response.failure("Invalid status: " + value));
        }

        try {
            BoardDTO board = boardService.addStatus(boardId, value);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/removeStatus")
    public ResponseEntity<Response<BoardDTO>> removeStatus(@RequestHeader long boardId, @RequestParam String status) {
        logger.info("in BoardController.removeStatus()");

        try {
            BoardDTO board = boardService.removeStatus(boardId, status);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/addType")
    public ResponseEntity<Response<BoardDTO>> addType(@RequestHeader long boardId, @RequestParam String value) {
        logger.info("in BoardController.addType()");

        if (!InputValidation.isValidLabel(value)) {
            return ResponseEntity.badRequest().body(Response.failure("Invalid type: " + value));
        }

        try {
            BoardDTO board = boardService.addType(boardId, value);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/removeType")
    public ResponseEntity<Response<BoardDTO>> removeType(@RequestHeader long boardId, @RequestParam String value) {
        logger.info("in BoardController.removeType()");

        try {
            BoardDTO board = boardService.removeType(boardId, value);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addItem")
    public ResponseEntity<Response<BoardDTO>> addItem(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.addItem()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            BoardDTO board = boardService.addItem(itemService.createItem(itemRequest));
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/removeItem")
    public ResponseEntity<Response<BoardDTO>> removeItem(@RequestHeader long boardId, @RequestParam long itemId) {
        logger.info("in BoardController.removeItem()");

        try {
            itemService.deleteItem(itemId);
            BoardDTO board = boardService.getBoardById(boardId);
            board.setNotifications(notifyBoardUsers(boardId, BoardAction.DELETE_ITEM));
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/updateItem")
    public ResponseEntity<Response<BoardDTO>> updateItem(@RequestHeader long boardId, @RequestHeader BoardAction action,
                                                         @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.updateItem()");

        try {
            itemRequest.setBoardId(boardId);
            itemRequest.setBoardAction(action);
            validateItemRequest(itemRequest);

            itemService.updateItem(itemRequest);

            BoardDTO board = boardService.getBoardById(itemRequest.getBoardId());
            board.setNotifications(notifyBoardUsers(boardId, action));

            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/addItemComment")
    public ResponseEntity<Response<BoardDTO>> addItemComment(@RequestHeader long boardId, @RequestBody CommentRequest commentRequest) {
        logger.info("in BoardController.addItemComment()");

        try {
            itemService.addComment(commentRequest.getItemId(), commentRequest.getUserId(), commentRequest.getContent());
            BoardDTO board = boardService.getBoardById(boardId);
            board.setNotifications(notifyBoardUsers(boardId, BoardAction.ADD_COMMENT));
            socketUtil.updateBoard(board);

            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/grantUserRole")
    public ResponseEntity<Response<BoardDTO>> grantUserRole(@RequestHeader long boardId, @RequestBody RoleRequest roleRequest) {
        logger.info("in BoardController.grantUserRole()");

        try {
            BoardDTO board = userRoleService.addByEmail(boardId, roleRequest.getEmail(), roleRequest.getRole());
            board.setNotifications(notifyBoardUsers(boardId, BoardAction.GRANT_USER_ROLE));
            socketUtil.updateBoard(board);

            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getBoardsByUserId")
    public ResponseEntity<Response<List<BoardDTO>>> getBoardsByUserId(@RequestAttribute long userId) {
        logger.info("in BoardController.getBoardsByUserId()");
        try {
            List<BoardDTO> boards = boardService.getBoardsByUserId(userId);
            return ResponseEntity.ok(Response.success(boards));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public ResponseEntity<Response<Void>> delete(@RequestHeader long boardId) {
        logger.info("in BoardController.deleteBoard()");

        try {
            boardService.delete(boardId);
            return ResponseEntity.ok(Response.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    private void validateItemRequest(ItemRequest itemRequest) {
        if (itemRequest.getStatus() != null &&
                !boardService.hasStatus(itemRequest.getBoardId(), itemRequest.getStatus())) {
            throw new IllegalArgumentException(String.format("Item's status %s does not exists in board #%d",
                    itemRequest.getStatus(), itemRequest.getBoardId()));
        }
        if (itemRequest.getType() != null &&
                !boardService.hasType(itemRequest.getBoardId(), itemRequest.getType())) {
            throw new IllegalArgumentException(String.format("Item's type %s does not exists in board #%d",
                    itemRequest.getType(), itemRequest.getBoardId()));
        }
    }

    private List<NotificationResponse> notifyBoardUsers(long boardId, BoardAction action) {
        return notificationService.notifyAll(userRoleService.getByBoard(boardId)
                .stream().map(AuthorizedUser::getUser).collect(Collectors.toList()), boardId, action);
    }
}
