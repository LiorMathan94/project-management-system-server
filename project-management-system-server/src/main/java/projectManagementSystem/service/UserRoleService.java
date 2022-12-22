package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.entity.*;
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

    public boolean isAuthorized(long boardId, long userId, BoardAction action) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Could not find user ID: " + userId);
        }

        List<UserInBoard> userInBoard = userInBoardRepository.findByBoardAndUser(board.get(), user.get());

        return (!userInBoard.isEmpty() && userInBoard.get(0).getRole().ordinal() <= action.getRole().ordinal());
    }

    public UserInBoard addByEmail(long boardId, String emailOfAssignedUser, Role role) {
        Optional<User> user = userRepository.findByEmail(emailOfAssignedUser);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Could not find user with email: " + emailOfAssignedUser);
        }

        return add(boardId,user.get().getId(),role);
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

    public List<UserInBoard> getByBoard(long boardId) {
        Optional<Board> board = this.boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return this.userInBoardRepository.findByBoard(board.get());
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
        if (userInBoard.getRole() == Role.ADMIN && role != Role.ADMIN) {
            throw new IllegalArgumentException("Cannot change admin role!");
        }

        userInBoard.setRole(role);
    }


}
