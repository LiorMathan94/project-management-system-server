package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;

import java.util.Optional;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private ItemRepository itemRepository;

    public BoardService(BoardRepository boardRepository, ItemRepository itemRepository) {
        this.boardRepository = boardRepository;
        this.itemRepository = itemRepository;
    }

    public BoardDTO createBoard(BoardRequest boardRequest) {
        Board board = new Board(boardRequest.getTitle(), boardRequest.getStatuses(), boardRequest.getTypes());
        return new BoardDTO(boardRepository.save(board));
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

    public BoardDTO removeItem(long boardId, Item item) {
        Optional<Board> board = boardRepository.findById(boardId);

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        board.get().removeItem(item);
        return new BoardDTO(boardRepository.save(board.get()));
    }

    private void deleteStatusItems(Board board, String status) {
        for (Item item : board.getItemsByStatus().get(status)) {
            board.removeItem(item);
            this.itemRepository.delete(item);
        }
    }
}
