package projectManagementSystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.CommentRequest;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.service.*;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {
    private final BoardService boardService;
    private final ItemService itemService;
    private final NotificationService notificationService;
    private final SocketUtil socketUtil;

    private static final Logger logger = LogManager.getLogger(ItemController.class.getName());

    @Autowired
    public ItemController(BoardService boardService, ItemService itemService,
                          NotificationService notificationService, SocketUtil socketUtil) {
        this.boardService = boardService;
        this.itemService = itemService;
        this.notificationService = notificationService;
        this.socketUtil = socketUtil;
    }

    /**
     * Adds a new item to the board corresponds to boardId.
     *
     * @param boardId
     * @param itemRequest
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addItem")
    public ResponseEntity<Response<BoardDTO>> addItem(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in ItemController.addItem()");

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

    /**
     * Deletes an item from the board corresponds to boardId.
     *
     * @param boardId
     * @param itemId
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/removeItem")
    public ResponseEntity<Response<BoardDTO>> removeItem(@RequestHeader long boardId, @RequestParam long itemId) {
        logger.info("in ItemController.removeItem()");

        try {
            boardService.deleteItem(boardId, itemId);
            BoardDTO board = boardService.getBoardById(boardId);
            notificationService.notifyAll(board, BoardAction.DELETE_ITEM);
            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Updates an existing item of the board corresponds to boardId.
     *
     * @param boardId
     * @param action
     * @param itemRequest
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.PATCH, path = "/updateItem")
    public ResponseEntity<Response<BoardDTO>> updateItem(@RequestHeader long boardId, @RequestHeader BoardAction action,
                                                         @RequestBody ItemRequest itemRequest) {
        logger.info("in ItemController.updateItem()");

        try {
            itemRequest.setBoardId(boardId);
            itemRequest.setBoardAction(action);
            validateItemRequest(itemRequest);

            itemService.updateItem(itemRequest);

            BoardDTO board = boardService.getBoardById(itemRequest.getBoardId());
            notificationService.notifyAll(board, action);

            socketUtil.updateBoard(board);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Adds a new comment to the board that corresponds to boardId.
     *
     * @param boardId
     * @param commentRequest
     * @param userId
     * @return the updated board's DTO version
     */
    @RequestMapping(method = RequestMethod.PATCH, path = "/addItemComment")
    public ResponseEntity<Response<BoardDTO>> addItemComment(@RequestHeader long boardId, @RequestBody CommentRequest commentRequest,
                                                             @RequestAttribute long userId) {
        logger.info("in ItemController.addItemComment()");

        try {
            itemService.addComment(commentRequest.getItemId(), userId, commentRequest.getContent());
            BoardDTO board = boardService.getBoardById(boardId);
            notificationService.notifyAll(board, BoardAction.ADD_COMMENT);
            socketUtil.updateBoard(board);

            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    /**
     * Validates ItemRequest.
     * if request is not valid - throws IllegalArgumentException.
     *
     * @param itemRequest
     */
    private void validateItemRequest(ItemRequest itemRequest) {
        if (itemRequest.getStatus() != null &&
                !boardService.hasStatus(itemRequest.getBoardId(), itemRequest.getStatus())) {
            throw new IllegalArgumentException(String.format("Item's status %s does not exists in board #%d",
                    itemRequest.getStatus(), itemRequest.getBoardId()));
        }

        String type = (itemRequest.getType() != null && itemRequest.getType().equals("")) ? null : itemRequest.getType();
        itemRequest.setType(type);
        if (itemRequest.getType() != null &&
                !boardService.hasType(itemRequest.getBoardId(), itemRequest.getType())) {
            throw new IllegalArgumentException(String.format("Item's type %s does not exists in board #%d",
                    itemRequest.getType(), itemRequest.getBoardId()));
        }
    }
}
