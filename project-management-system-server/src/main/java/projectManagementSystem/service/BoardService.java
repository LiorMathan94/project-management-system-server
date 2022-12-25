package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private BoardRepository boardRepository;
//    private UserInBoardRepository userInBoardRepository;
    private UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
//        this.userInBoardRepository = userInBoardRepository;
        this.userRepository = userRepository;
    }

    public BoardDTO getBoardById(long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        return new BoardDTO(board.get());
    }

    public BoardDTO createBoard(BoardRequest boardRequest) {
        Board board = new Board(boardRequest.getTitle(), boardRequest.getStatuses(), boardRequest.getTypes());
        Board savedBoard = boardRepository.save(board);

        return new BoardDTO(savedBoard);
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

    public BoardDTO addItem(Item item) {
        Optional<Board> board = boardRepository.findById(item.getBoardId());
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + item.getBoardId());
        }

        board.get().addItem(item);
        return new BoardDTO(boardRepository.save(board.get()));
    }

//    public BoardDTO removeItem(long boardId, long itemId) {
//        Optional<Board> board = boardRepository.findById(boardId);
//        if (!board.isPresent()) {
//            throw new IllegalArgumentException("Could not find board ID: " + boardId);
//        }
//
//        Optional<Item> item = board.get().getItemById(itemId);
//        if (!item.isPresent()) {
//            throw new IllegalArgumentException("Could not find item ID: " + itemId);
//        }
//
//        board.get().removeItem(item.get());
//        return new BoardDTO(boardRepository.save(board.get()));
//    }

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
        boardRepository.deleteById(boardId);
    }

    public List<BoardDTO> getBoardsByUserId(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User ID: "+ userId +" does not exist");
        }

        List<Board> boards = boardRepository.getBoardsByUser(userId);
        return boards.stream().map(BoardDTO::new).collect(Collectors.toList());
    }
}
