//package projectManagementSystem.controller;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import projectManagementSystem.controller.request.BoardRequest;
//import projectManagementSystem.controller.response.Response;
//import projectManagementSystem.entity.Board;
//import projectManagementSystem.entity.DTO.BoardDTO;
//import projectManagementSystem.entity.Role;
//import projectManagementSystem.service.BoardService;
//import projectManagementSystem.service.ItemService;
//import projectManagementSystem.service.UserRoleService;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = BoardController.class)
//public class BoardControllerTests {
//    @MockBean
//    private BoardService boardService;
//    @MockBean
//    private ItemService itemService;
//    @MockBean
//    private UserRoleService userRoleService;
//    @MockBean
//    private BoardController boardController;
//    @Autowired
//    private MockMvc mockMvc;
//
//
////    @Test
////    @DisplayName("create() returns a valid board DTO response when BoardRequest is valid")
////    void create_ValidBoardRequest_ReturnsValidBoardDTO() throws Exception {
////        BoardRequest boardRequest = createValidBoardRequest();
////        BoardDTO boardDTO = new BoardDTO(createBoard());
////        long randomUserId = 1;
////
////        Mockito.when(boardService.createBoard(boardRequest)).thenReturn(boardDTO);
////        Mockito.when(userRoleService.add(boardDTO.getId(), randomUserId, Role.ADMIN)).thenReturn(null);
////
////
////        ResponseEntity response = boardController.create(randomUserId, boardRequest);
////        mockMvc.perform(post("/board/create")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(randomUserId, boardRequest))
////                .andExpect(status().isCreated());
////        assertEquals(ResponseEntity.ok(Response.success(boardDTO)), response,
////                "BoardController should return a valid BoardDTO response when receiving a valid request");
////    }
//
//    @Test
//    void create_InvalidBoardRequest_Fails() throws Exception {
//        mockMvc.perform(post("/board/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isBadRequest());
//    }
//
//    private Board createBoard() {
//        BoardRequest boardRequest = createValidBoardRequest();
//        Board board = new Board(boardRequest.getTitle(), boardRequest.getStatuses(), boardRequest.getTypes());
//
//        return board;
//    }
//
//    private BoardRequest createValidBoardRequest() {
//        Set<String> statuses = new HashSet<>();
//        statuses.add("To Do");
//        statuses.add("Done");
//
//        Set<String> types = new HashSet<>();
//        types.add("Task");
//        types.add("Bug");
//
//        return new BoardRequest("Test", statuses, types);
//    }
//}
