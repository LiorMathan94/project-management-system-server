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

    /**
     * Constructor for UserRoleService
     *
     * @param boardRepository
     * @param userRepository
     */
    public UserRoleService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    /**
     * Checks if user corresponds to userId is authorized to perform action in board corresponds to boardId.
     *
     * @param boardId
     * @param userId
     * @param action
     * @return true if user is authorized, otherwise - false.
     */
    @Transactional
    public boolean isAuthorized(long boardId, long userId, BoardAction action) {
        if (action.getRole() == null) {
            return true;
        }

        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Optional<AuthorizedUser> authorized = board.get().getAuthorizedById(userId);

        return (authorized.isPresent() && authorized.get().getRole().ordinal() <= action.getRole().ordinal());
    }

    /**
     * Adds the user with the given email as an authorized user of the board.
     *
     * @param boardId
     * @param email
     * @param role
     * @return the board's DTO version
     */
    public BoardDTO addByEmail(long boardId, String email, Role role) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Could not find user with email: " + email);
        }

        return add(boardId, user.get().getId(), role);
    }

    /**
     * Adds the user corresponds to userId as an authorized user of the board.
     *
     * @param boardId
     * @param userId
     * @param role
     * @return the board's DTO version
     */
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
        return BoardDTO.createFromBoard(this.boardRepository.save(board.get()));
    }

    /**
     * @param boardId
     * @return all authorized users of the board that corresponds to boardId.
     */
    public List<AuthorizedUser> getByBoard(long boardId) {
        Optional<Board> board = this.boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return board.get().getAuthorizedUsers();
    }
}
