package projectManagementSystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.controller.request.FilterRequest;
import projectManagementSystem.controller.request.RoleRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Role;
import projectManagementSystem.service.*;
import projectManagementSystem.utils.InputValidation;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserRoleService userRoleService;
    private final NotificationService notificationService;
    private final FilterCriteriaService filterCriteriaService;
    private final SocketUtil socketUtil;

    private static final Logger logger = LogManager.getLogger(BoardController.class.getName());

    /**
     * Constructor for BoardController
     * @param boardService
     * @param userRoleService
     * @param notificationService
     * @param socketUtil
     * @param filterCriteriaService
     */
    @Autowired
    public BoardController(BoardService boardService, UserRoleService userRoleService, NotificationService notificationService,
                           SocketUtil socketUtil,FilterCriteriaService filterCriteriaService) {
        this.boardService = boardService;
        this.notificationService = notificationService;
        this.userRoleService = userRoleService;
        this.filterCriteriaService = filterCriteriaService;
        this.socketUtil = socketUtil;
    }

    /**
     * Creates a new board and stores it in the database.
     * @param userId
     * @param boardRequest
     * @return the new board's DTO version
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ResponseEntity<Response<BoardDTO>> create(@RequestAttribute long userId, @RequestBody BoardRequest boardRequest) {
        logger.info("in BoardController.create()");

        try {
            if (!InputValidation.isValidLabel(boardRequest.getTitle())) {
                return ResponseEntity.badRequest().body(Response.failure("Invalid title: " + boardRequest.getTitle()));
            }

            BoardDTO board = boardService.createBoard(boardRequest);
            this.userRoleService.add(board.getId(), userId, Role.ADMIN);
            return ResponseEntity.ok(Response.success(board));
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error in BoardController.create() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.create() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getBoardById")
    public ResponseEntity<Response<BoardDTO>> getBoardById(@RequestHeader long boardId) {
        logger.info("in BoardController.getBoardById()");

        try {
            BoardDTO board = boardService.getBoardById(boardId);
            return ResponseEntity.ok(Response.success(board));
        } catch (IllegalArgumentException e) {
            logger.error("Error in BoardController.getBoardById() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.getBoardById() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Updates board's title with the given value.
     * @param boardId
     * @param value
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.PATCH, path = "/title")
    public ResponseEntity<Response<BoardDTO>> setTitle(@RequestHeader long boardId, @RequestParam String value) {
        logger.info("in BoardController.setTitle()");

        if (!InputValidation.isValidLabel(value)) {
            return ResponseEntity.badRequest().body(Response.failure("Invalid title: " + value));
        }

        try {
            BoardDTO board = boardService.setTitle(boardId, value);
            notificationService.notifyAll(board, BoardAction.SET_TITLE);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error in BoardController.setTitle() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.setTitle() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Adds status with the given value to the board's statuses.
     * @param boardId
     * @param value
     * @return the updated board's DTO version
     */
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
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error in BoardController.addStatus() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.addStatus() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Removes status from board's statuses.
     * Deletes all board's items from the given status.
     * @param boardId
     * @param status
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/removeStatus")
    public ResponseEntity<Response<BoardDTO>> removeStatus(@RequestHeader long boardId, @RequestParam String status) {
        logger.info("in BoardController.removeStatus()");

        try {
            BoardDTO board = boardService.removeStatus(boardId, status);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error in BoardController.removeStatus() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.removeStatus() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Adds type with the given value to the board's types.
     * @param boardId
     * @param value
     * @return the updated board's DTO version
     */
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
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error in BoardController.addType() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.addType() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Removes type with the given value from board's types.
     * @param boardId
     * @param value
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.PATCH, path = "/removeType")
    public ResponseEntity<Response<BoardDTO>> removeType(@RequestHeader long boardId, @RequestParam String value) {
        logger.info("in BoardController.removeType()");

        try {
            BoardDTO board = boardService.removeType(boardId, value);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error in BoardController.removeType() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.removeType() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }


    /**
     * Grants permission to a user for the board that corresponds to boardId.
     * @param boardId
     * @param roleRequest
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.PATCH, path = "/grantUserRole")
    public ResponseEntity<Response<BoardDTO>> grantUserRole(@RequestHeader long boardId, @RequestBody RoleRequest roleRequest) {
        logger.info("in BoardController.grantUserRole()");

        try {
            BoardDTO board = userRoleService.addByEmail(boardId, roleRequest.getEmail(), roleRequest.getRole());
            notificationService.notifyAll(board, BoardAction.GRANT_USER_ROLE);
            socketUtil.updateBoard(board);

            return ResponseEntity.ok(Response.success(board));
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error in BoardController.grantUserRole() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.grantUserRole() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * @param userId
     * @return a DTO version of all user's boards
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getBoardsByUserId")
    public ResponseEntity<Response<List<BoardDTO>>> getBoardsByUserId(@RequestAttribute long userId) {
        logger.info("in BoardController.getBoardsByUserId()");
        try {
            List<BoardDTO> boards = boardService.getBoardsByUserId(userId);
            return ResponseEntity.ok(Response.success(boards));
        } catch (IllegalArgumentException e) {
            logger.error("Error in BoardController.getBoardsByUserId() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.getBoardsByUserId() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * @param boardId
     * @return a DTO version of all user's boards
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getBoardsByBoardId")
    public ResponseEntity<Response<BoardDTO>> getBoardsByBoardId(@RequestHeader long boardId) {
        logger.info("in BoardController.getBoardsByBoardId()");
        try {
            BoardDTO boards = boardService.getBoardById(boardId);
            return ResponseEntity.ok(Response.success(boards));
        } catch (IllegalArgumentException e) {
            logger.error("Error in BoardController.getBoardsByBoardId() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.getBoardsByBoardId() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Deletes the board that corresponds to boardId
     * @param boardId
     * @return void
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public ResponseEntity<Response<Void>> delete(@RequestHeader long boardId) {
        logger.info("in BoardController.deleteBoard()");

        try {
            boardService.delete(boardId);
            return ResponseEntity.ok(Response.success(null));
        } catch (IllegalArgumentException e) {
            logger.error("Error in BoardController.delete() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.delete() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * @param boardId
     * @param filterRequest
     * @return all board's items that meet filter criteria
     */
    @RequestMapping(method = RequestMethod.POST, path = "/filter")
    public ResponseEntity<Response<BoardDTO>> filterByProperty(@RequestHeader long boardId, @RequestBody FilterRequest filterRequest) {
        logger.info("in BoardController.filterByProperty()");
        try {
            BoardDTO board = filterCriteriaService.getFilteredBoard(boardId, filterRequest);
            return ResponseEntity.ok(Response.success(board));
        } catch (IllegalArgumentException e) {
            logger.error("Error in BoardController.filterByProperty() " + e.getMessage());
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error in BoardController.filterByProperty() " + e);
            return ResponseEntity.internalServerError().body(Response.failure(e.getMessage()));
        }
    }
}
