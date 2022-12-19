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
    public ResponseEntity<Response<BoardDTO>> setTitle(@RequestHeader long boardId, @RequestParam String title) {
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
    public ResponseEntity<Response<BoardDTO>> addStatus(@RequestHeader long boardId, @RequestParam String status) {
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
    public ResponseEntity<Response<BoardDTO>> removeStatus(@RequestHeader long boardId, @RequestParam String status) {
        logger.info("in BoardController.removeStatus()");

        try {
            BoardDTO board = boardService.removeStatus(boardId, status);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/addType")
    public ResponseEntity<Response<BoardDTO>> addType(@RequestHeader long boardId, @RequestParam String type) {
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
    public ResponseEntity<Response<BoardDTO>> removeType(@RequestHeader long boardId, @RequestParam String type) {
        logger.info("in BoardController.removeType()");

        try {
            BoardDTO board = boardService.removeType(boardId, type);
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
            BoardDTO board = boardService.addItem(boardId, itemService.createItem(itemRequest));
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/removeItem")
    public ResponseEntity<Response<BoardDTO>> removeItem(@RequestHeader long boardId, @RequestParam long itemId) {
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

    @RequestMapping(method = RequestMethod.PATCH, path = "/assignItem")
    public ResponseEntity<Response<BoardDTO>> assignItem(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.assignItem()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.assign(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/setItemImportance")
    public ResponseEntity<Response<BoardDTO>> setItemImportance(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.setItemImportance()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.setImportance(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/setItemDueDate")
    public ResponseEntity<Response<BoardDTO>> setItemDueDate(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.setItemDueDate()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.setDueDate(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/setItemStatus")
    public ResponseEntity<Response<BoardDTO>> setItemStatus(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.setItemStatus()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.setStatus(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/setItemTitle")
    public ResponseEntity<Response<BoardDTO>> setItemTitle(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.setItemTitle()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.setTitle(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/setItemDescription")
    public ResponseEntity<Response<BoardDTO>> setItemDescription(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.setItemDescription()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.setDescription(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/setItemParent")
    public ResponseEntity<Response<BoardDTO>> setItemParent(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.setItemParent()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.setParent(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/setItemType")
    public ResponseEntity<Response<BoardDTO>> setItemType(@RequestHeader long boardId, @RequestBody ItemRequest itemRequest) {
        logger.info("in BoardController.setItemType()");

        try {
            itemRequest.setBoardId(boardId);
            validateItemRequest(itemRequest);

            Item updatedItem = itemService.setType(itemRequest);
            BoardDTO board = boardService.updateItem(itemRequest.getBoardId(), updatedItem);
            return ResponseEntity.ok(Response.success(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/grantUserRole")
    public ResponseEntity<Response<UserInBoardDTO>> grantUserRole(@RequestHeader long boardId, @RequestBody RoleRequest roleRequest) {
        logger.info("in BoardController.grantUserRole()");

        try {
            return ResponseEntity.ok(Response.success(
                    new UserInBoardDTO(userRoleService.add
                            (boardId, roleRequest.getUserId(), roleRequest.getRole()))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public ResponseEntity<Response<Void>> delete(@RequestHeader long boardId) {
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
}
