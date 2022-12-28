package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;
import projectManagementSystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    /**
     * Constructor for BoardService.
     * @param boardRepository
     * @param userRepository
     */
    public BoardService(BoardRepository boardRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    /**
     * @param boardId
     * @return the board's DTO version
     */
    public BoardDTO getBoardById(long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return BoardDTO.createFromBoard(board.get());
    }

    /**
     * Creates a new board and stores it in the database.
     * @param boardRequest
     * @return the new board's DTO version
     */
    public BoardDTO createBoard(BoardRequest boardRequest) {
        Board board = new Board(boardRequest.getTitle(), boardRequest.getStatuses(), boardRequest.getTypes());
        Board savedBoard = boardRepository.save(board);

        return BoardDTO.createFromBoard(savedBoard);
    }

    /**
     * Sets board's title
     * @param boardId
     * @param title
     * @return the updated board's DTO version
     */
    public BoardDTO setTitle(long boardId, String title) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().setTitle(title);
        return BoardDTO.createFromBoard(boardRepository.save(board.get()));
    }

    /**
     * Adds status to board's statuses.
     * @param boardId
     * @param status
     * @return the updated board's DTO version
     */
    public BoardDTO addStatus(long boardId, String status) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().addStatus(status);
        return BoardDTO.createFromBoard(boardRepository.save(board.get()));
    }

    /**
     * Removes status from board's statuses.
     * @param boardId
     * @param status
     * @return the updated board's DTO version
     */
    public BoardDTO removeStatus(long boardId, String status) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        for (Item item : board.get().getItemsByStatus().get(status)) {
            List<Item> subItems = itemRepository.getItemsByParent(item);
            for (int i = 0; i < subItems.size(); i++) {
                subItems.get(i).setParent(null);
                itemRepository.save(subItems.get(i));
            }
        }

        board.get().removeStatus(status);
        return BoardDTO.createFromBoard(boardRepository.save(board.get()));
    }

    /**
     * Adds type to board's types
     * @param boardId
     * @param type
     * @return the updated board's DTO version
     */
    public BoardDTO addType(long boardId, String type) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().addType(type);
        return BoardDTO.createFromBoard(boardRepository.save(board.get()));
    }

    /**
     * Removes type from board's types
     * @param boardId
     * @param type
     * @return the updated board's DTO version
     */
    public BoardDTO removeType(long boardId, String type) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().removeType(type);
        return BoardDTO.createFromBoard(boardRepository.save(board.get()));
    }

    /**
     * Adds a new item to a board.
     * @param item
     * @return the updated board's DTO version
     */
    public BoardDTO addItem(Item item) {
        Optional<Board> board = boardRepository.findById(item.getBoardId());
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + item.getBoardId());
        }

        board.get().addItem(item);
        return BoardDTO.createFromBoard(boardRepository.save(board.get()));
    }

    /**
     * @param boardId
     * @param status
     * @return true if the board's statuses contain status, otherwise - false
     */
    public boolean hasStatus(long boardId, String status) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return board.get().getStatuses().contains(status);
    }

    /**
     * @param boardId
     * @param type
     * @return true if the board's types contain type, otherwise - false
     */
    public boolean hasType(long boardId, String type) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return board.get().getTypes().contains(type);
    }

    /**
     * Deletes a board.
     * @param boardId
     */
    public void delete(long boardId) {
        boardRepository.deleteById(boardId);
    }

    /**
     * @param userId
     * @return all user's boards
     */
    public List<BoardDTO> getBoardsByUserId(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User ID: " + userId + " does not exist");
        }

        List<Board> boards = boardRepository.getBoardsByUser(userId);
        return boards.stream().map(BoardDTO::createFromBoard).collect(Collectors.toList());
    }

}