package projectManagementSystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.controller.request.RoleRequest;
import projectManagementSystem.controller.response.Response;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.DTO.UserInBoardDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.Role;
import projectManagementSystem.service.BoardService;
import projectManagementSystem.service.ItemService;
import projectManagementSystem.service.UserRoleService;
import projectManagementSystem.utils.InputValidation;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserRoleService userRoleService;
    private static final Logger logger = LogManager.getLogger(BoardController.class.getName());

    public BoardController() {
    }



    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ResponseEntity<Response<BoardDTO>> create(@RequestBody BoardRequest boardRequest) {
        logger.info("in BoardController.create()");

        try {
            BoardDTO board = boardService.createBoard(boardRequest);
            this.userRoleService.add(board.getId(), boardRequest.getUserId(), Role.ADMIN);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/title")
    public ResponseEntity<Response<BoardDTO>> setTitle(@RequestParam long boardId, @RequestParam String title) {
        logger.info("in BoardController.setTitle()");

        if (!InputValidation.isValidLabel(title)) {
            return ResponseEntity.badRequest().body(Response.failure("Invalid title: " + title));
        }

        try {
            BoardDTO board = boardService.setTitle(boardId, title);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/addStatus")
    public ResponseEntity<Response<BoardDTO>> addStatus(@RequestParam long boardId, @RequestParam String status) {
        logger.info("in BoardController.addStatus()");

        if (!InputValidation.isValidLabel(status)) {
            return ResponseEntity.badRequest().body(Response.failure("Invalid status: " + status));
        }

        try {
            BoardDTO board = boardService.addStatus(boardId, status);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/removeStatus")
    public ResponseEntity<Response<BoardDTO>> removeStatus(@RequestParam long boardId, @RequestParam String status) {
        logger.info("in BoardController.removeStatus()");

        try {
            BoardDTO board = boardService.removeStatus(boardId, status);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/addType")
    public ResponseEntity<Response<BoardDTO>> addType(@RequestParam long boardId, @RequestParam String type) {
        logger.info("in BoardController.addType()");

        if (!InputValidation.isValidLabel(type)) {
            return ResponseEntity.badRequest().body(Response.failure("Invalid type: " + type));
        }

        try {
            BoardDTO board = boardService.addType(boardId, type);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/removeType")
    public ResponseEntity<Response<BoardDTO>> removeType(@RequestParam long boardId, @RequestParam String type) {
        logger.info("in BoardController.removeType()");

        try {
            BoardDTO board = boardService.removeType(boardId, type);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addItem")
    public ResponseEntity<Response<BoardDTO>> addItem(@RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.addItem()");

        try {
            validateItemRequest(itemRequest);
            BoardDTO board = boardService.addItem(itemRequest.getBoardId(), itemService.createItem(itemRequest));
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/removeItem")
    public ResponseEntity<Response<BoardDTO>> removeItem(@RequestParam long boardId, @RequestParam long itemId) {
        logger.info("in BoardController.removeItem()");

        Optional<Item> itemToDelete = itemService.getById(itemId);
        if (!itemToDelete.isPresent()) {
            return ResponseEntity.badRequest().body(Response.failure("Could not find item ID: " + itemId));
        }

        try {
            BoardDTO board = boardService.removeItem(boardId, itemToDelete.get());
            itemService.deleteItem(itemId);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/updateItem")
    public ResponseEntity<Response<BoardDTO>> updateItem(@RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.updateItem()");

        try {
            validateItemRequest(itemRequest);
            Item updatedItem = itemService.updateItem(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/grantUserRole")
    public ResponseEntity<Response<UserInBoardDTO>> grantUserRole(@RequestBody RoleRequest roleRequest) {
        logger.info("in BoardController.grantUserRole()");

        try {
            return ResponseEntity.ok(Response.success(
                    new UserInBoardDTO(userRoleService.add
                            (roleRequest.getBoardId(), roleRequest.getUserId(), roleRequest.getRole()))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public ResponseEntity<Response<Void>> delete(@RequestParam long boardId) {
        logger.info("in BoardController.deleteBoard()");

        try {
            userRoleService.deleteByBoard(boardId);
            boardService.delete(boardId);
            return ResponseEntity.ok(Response.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    private void validateItemRequest(ItemRequest itemRequest) {
        if (!boardService.hasStatus(itemRequest.getBoardId(), itemRequest.getStatus())) {
            throw new IllegalArgumentException(String.format("Item's status %s does not exists in board #%d",
                    itemRequest.getStatus(), itemRequest.getBoardId()));
        }
        if (!boardService.hasType(itemRequest.getBoardId(), itemRequest.getType())) {
            throw new IllegalArgumentException(String.format("Item's type %s does not exists in board #%d",
                    itemRequest.getType(), itemRequest.getBoardId()));
        }
    }

}
