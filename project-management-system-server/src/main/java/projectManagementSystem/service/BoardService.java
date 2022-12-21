package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.entity.*;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;
import projectManagementSystem.repository.UserInBoardRepository;
import projectManagementSystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private ItemRepository itemRepository;
    private UserInBoardRepository userInBoardRepository;
    private UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, ItemRepository itemRepository,
                        UserInBoardRepository userInBoardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.itemRepository = itemRepository;
        this.userInBoardRepository = userInBoardRepository;
        this.userRepository = userRepository;
    }

    public BoardDTO createBoard(BoardRequest boardRequest) {
        Board board = new Board(boardRequest.getTitle(), boardRequest.getStatuses(), boardRequest.getTypes());
        Board savedBoard = boardRepository.save(board);

        return new BoardDTO(savedBoard);
    }

    public BoardDTO join(long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return new BoardDTO(board.get());
    }

    public BoardDTO setTitle(long boardId, String title) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().setTitle(title);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO addStatus(long boardId, String status) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().addStatus(status);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO removeStatus(long boardId, String status) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        deleteStatusItems(board.get(), status);

        board.get().removeStatus(status);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO addType(long boardId, String type) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().addType(type);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO removeType(long boardId, String type) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().removeType(type);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO addItem(long boardId, Item item) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().addItem(item);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO updateItem(long boardId, Item item) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().updateItem(item);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO removeItem(long boardId, Item item) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().removeItem(item);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public boolean hasStatus(long boardId, String status) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return board.get().getStatuses().contains(status);
    }

    public boolean hasType(long boardId, String type) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return board.get().getTypes().contains(type);
    }

    public void delete(long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        for (Item item : board.get().getItems()) {
            this.itemRepository.delete(item);
        }

        this.boardRepository.delete(board.get());
    }

    public List<BoardDTO> getBoardsByUserId(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User ID: "+ userId +" does not exist");
        }

        return userInBoardRepository.findBoardsByUserId(user).stream()
                .map(userInBoard -> new BoardDTO(userInBoard.getBoard())).collect(Collectors.toList());
    }

    private void deleteStatusItems(Board board, String status) {
        for (Item item : board.getItemsByStatus().get(status)) {
            board.removeItem(item);
            this.itemRepository.delete(item);
        }
    }
}
