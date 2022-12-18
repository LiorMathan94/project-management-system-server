package projectManagementSystem.entity.DTO;

import projectManagementSystem.entity.Role;
import projectManagementSystem.entity.UserInBoard;

public class UserInBoardDTO {
    private BoardDTO board;
    private UserDTO user;
    private Role role;

    public UserInBoardDTO(UserInBoard userInBoard) {
        this.board = new BoardDTO(userInBoard.getBoard());
        this.user = new UserDTO(userInBoard.getUser());
        this.role = userInBoard.getRole();
    }

    public BoardDTO getBoard() {
        return board;
    }

    public UserDTO getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }
}
