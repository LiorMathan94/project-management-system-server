package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectManagementSystem.entity.*;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {
    private BoardRepository boardRepository;
    private UserRepository userRepository;


    public UserRoleService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean isAuthorized(long boardId, long userId, BoardAction action) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Optional<AuthorizedUser> authorized = board.get().getAuthorizedById(userId);

        return (authorized.isPresent() && authorized.get().getRole().ordinal() <= action.getRole().ordinal());
    }

    public BoardDTO addByEmail(long boardId, String email, Role role) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Could not find user with email: " + email);
        }

        return add(boardId, user.get().getId(), role);
    }

    public BoardDTO add(long boardId, long userId, Role role) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Could not find user ID: " + userId);
        }

        board.get().assignUser(user.get(), role);
        return new BoardDTO(this.boardRepository.save(board.get()));
    }

    public List<AuthorizedUser> getByBoard(long boardId) {
        Optional<Board> board = this.boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return board.get().getAuthorizedUsers();
    }
}
