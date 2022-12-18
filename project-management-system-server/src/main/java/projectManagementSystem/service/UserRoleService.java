package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.Role;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.UserInBoard;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.UserInBoardRepository;
import projectManagementSystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {
    private UserInBoardRepository userInBoardRepository;
    private BoardRepository boardRepository;
    private UserRepository userRepository;


    public UserRoleService(UserInBoardRepository userInBoardRepository, BoardRepository boardRepository,
                           UserRepository userRepository) {
        this.userInBoardRepository = userInBoardRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public UserInBoard add(long boardId, long userId, Role role) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Could not find user ID: " + userId);
        }

        List<UserInBoard> existingRoles = userInBoardRepository.findByBoardAndUser(board.get(), user.get());
        UserInBoard userRole = existingRoles.isEmpty() ? null : existingRoles.get(0);
        if (userRole != null) {
            updateExisting(userRole, role);
        } else {
            userRole = new UserInBoard(board.get(), user.get(), role);
        }

        return this.userInBoardRepository.save(userRole);
    }

    public void deleteByBoard(long boardId) {
        Optional<Board> board = this.boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        this.userInBoardRepository.deleteByBoard(board.get());
    }

    public void deleteByUser(long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Could not find user ID: " + userId);
        }

        this.userInBoardRepository.deleteByUser(user.get());
    }

    private void updateExisting(UserInBoard userInBoard, Role role) {
        if (userInBoard.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Cannot change admin role!");
        }

        userInBoard.setRole(role);
    }
}
