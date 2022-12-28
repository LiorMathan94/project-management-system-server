package projectManagementSystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.controller.request.RoleRequest;
import projectManagementSystem.controller.request.FilterRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Role;
import projectManagementSystem.entity.Importance;
import projectManagementSystem.service.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTests {
    @Mock
    private BoardService boardService;
    @Mock
    private ItemService itemService;
    @Mock
    private UserRoleService userRoleService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private FilterCriteriaService filterCriteriaService;
    @Mock
    private SocketUtil socketUtil;
    @InjectMocks
    private BoardController boardController;
    private BoardRequest validBoardRequest;
    private BoardDTO boardDTOSuccess;

    @BeforeEach
    void beforeEach() {
        String title = "board title";

        Set<String> statuses = new HashSet<>();
        statuses.add("to do");
        statuses.add("done");

        Set<String> types = new HashSet<>();
        types.add("task");
        types.add("bug");

        validBoardRequest = new BoardRequest(title, statuses, types);
        boardDTOSuccess = BoardDTO.createFromBoard(new Board(title, statuses, types));
    }

    @Test
    void create_validBoardRequest_ResponseEntityOk() {
        long userId = 1L;

        given(boardService.createBoard(validBoardRequest)).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.create(userId, validBoardRequest).getStatusCodeValue(), "Create with valid userId and valid BoardRequest did not return ResponseEntity status 200 (OK)");
    }

    @Test
    void create_BadRequest_ResponseEntityBadRequest() {
        assertEquals(400, boardController.create(1, null).getStatusCodeValue(), "create() with invalid BoardRequest should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void setTitle_validTitle_ResponseEntityOk() {
        given(boardService.setTitle(boardDTOSuccess.getId(), boardDTOSuccess.getTitle())).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.setTitle(boardDTOSuccess.getId(), boardDTOSuccess.getTitle()).getStatusCodeValue(), "setTitle() with valid title should return ResponseEntity status 200 (OK)");
    }

    @Test
    void setTitle_EmptyTitle_ResponseEntityBadRequest() {
        assertEquals(400, boardController.setTitle(boardDTOSuccess.getId(), "").getStatusCodeValue(), "setTitle() with empty title should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void setTitle_LongTitle_ResponseEntityBadRequest() {
        String tooLongTitle = "tiiiiiiiiiiiiiitleeeeeeeeeeeeeeeeee";
        assertEquals(400, boardController.setTitle(boardDTOSuccess.getId(), tooLongTitle).getStatusCodeValue(), "setTitle() with title of size > 20 characters should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void addStatus_validStatus_ResponseEntityOk() {
        String validStatus = "new valid status";
        given(boardService.addStatus(boardDTOSuccess.getId(), validStatus)).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.addStatus(boardDTOSuccess.getId(), validStatus).getStatusCodeValue(), "addStatus() with valid status should return ResponseEntity status 200 (OK)");
    }

    @Test
    void addStatus_EmptyStatus_ResponseEntityBadRequest() {
        assertEquals(400, boardController.addStatus(boardDTOSuccess.getId(), "").getStatusCodeValue(), "addStatus() with empty status should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void addStatus_LongTitle_ResponseEntityBadRequest() {
        String tooLongStatus = "staaaaaaaaaaaaatuuuuuuuusssssss";
        assertEquals(400, boardController.addStatus(boardDTOSuccess.getId(), tooLongStatus).getStatusCodeValue(), "addStatus() with status of size > 20 characters should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void removeStatus_validStatus_ResponseEntityOk() {
        String validStatus = "to do";
        given(boardService.removeStatus(boardDTOSuccess.getId(), validStatus)).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.removeStatus(boardDTOSuccess.getId(), validStatus).getStatusCodeValue(), "removeStatus() with valid status should return ResponseEntity status 200 (OK)");
    }

    @Test
    void addType_validType_ResponseEntityOk() {
        String validType = "new valid type";
        given(boardService.addType(boardDTOSuccess.getId(), validType)).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.addType(boardDTOSuccess.getId(), validType).getStatusCodeValue(), "addType() with valid type should return ResponseEntity status 200 (OK)");
    }

    @Test
    void addType_EmptyType_ResponseEntityBadRequest() {
        assertEquals(400, boardController.addType(boardDTOSuccess.getId(), "").getStatusCodeValue(), "addType() with empty type should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void addType_LongType_ResponseEntityBadRequest() {
        String tooLongType = "tyyyyyyyyyyyyyyypeeeeeeeeee";
        assertEquals(400, boardController.addType(boardDTOSuccess.getId(), tooLongType).getStatusCodeValue(), "addType() with type of size > 20 characters should return ResponseEntity status 400 (Bad Request)");
    }

    @Test
    void removeType_validType_ResponseEntityOk() {
        String validType = "task";
        given(boardService.removeType(boardDTOSuccess.getId(), validType)).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.removeType(boardDTOSuccess.getId(), validType).getStatusCodeValue(), "removeType() with valid type should return ResponseEntity status 200 (OK)");
    }

    @Test
    void filterByProperty_emptyFilterRequest_ResponseEntityOK() {
        FilterRequest filterRequest = new FilterRequest();

        List<Importance> importanceList = new ArrayList<>();
        importanceList.add(Importance.LEVEL1);
        filterRequest.setImportance(importanceList);

        given(filterCriteriaService.getFilteredBoard(boardDTOSuccess.getId(), filterRequest)).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.filterByProperty(boardDTOSuccess.getId(), filterRequest).getStatusCodeValue(), "filterByProperty() with valid type should return ResponseEntity status 200 (OK)");
    }

    @Test
    void grantUserRole_validRoleRequest_ResponseEntityOk() {
        RoleRequest roleRequest = new RoleRequest("lior.mathan@gmail.com", Role.USER);
        given(userRoleService.addByEmail(boardDTOSuccess.getId(), roleRequest.getEmail(), roleRequest.getRole())).willReturn(boardDTOSuccess);
        assertEquals(200, boardController.grantUserRole(boardDTOSuccess.getId(), roleRequest).getStatusCodeValue(), "grantUserRole() with valid RoleRequest should return ResponseEntity status 200 (OK)");
    }
}