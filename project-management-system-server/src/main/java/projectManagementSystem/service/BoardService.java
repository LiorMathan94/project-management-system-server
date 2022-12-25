package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.*;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.UserInBoardRepository;
import projectManagementSystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private UserInBoardRepository userInBoardRepository;
    private UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserInBoardRepository userInBoardRepository,
                        UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userInBoardRepository = userInBoardRepository;
        this.userRepository = userRepository;
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

    public BoardDTO addItem(ItemRequest itemRequest) {
        Optional<Board> board = boardRepository.findById(itemRequest.getBoardId());

        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + itemRequest.getBoardId());
        }

        board.get().addItem(createItem(itemRequest));
        return new BoardDTO(boardRepository.save(board.get()));
    }

    private Item createItem(ItemRequest itemRequest) {
        Item parent = extractParentFromItemRequest(itemRequest);

        Item newItem = new Item.ItemBuilder(itemRequest.getBoardId(), itemRequest.getCreatorId(), itemRequest.getTitle())
                .setAssignedToId(itemRequest.getAssignedToId())
                .setDescription(itemRequest.getDescription())
                .setDueDate(itemRequest.getDueDate())
                .setImportance(itemRequest.getImportance())
                .setParent(parent)
                .setStatus(itemRequest.getStatus())
                .setType(itemRequest.getType())
                .build();

        return newItem;
    }

    private Item extractParentFromItemRequest(ItemRequest itemRequest) {
        Optional<Board> board = boardRepository.findById(itemRequest.getBoardId());
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + itemRequest.getBoardId());
        }

        Item parent = null;
        if (itemRequest.getParentId() != null) {
            Optional<Item> parentOptional = board.get().getItemById(itemRequest.getParentId());
            parent = parentOptional.isPresent() ? parentOptional.get() : null;
        }

        return parent;
    }

    public BoardDTO updateItem(ItemRequest itemRequest) {
        Optional<Board> board = boardRepository.findById(itemRequest.getBoardId());
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + itemRequest.getBoardId());
        }

        Optional<Item> item = board.get().getItemById(itemRequest.getItemId());
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemRequest.getItemId());
        }

        updateBoardItem(item.get(), board.get(), itemRequest);
        board.get().updateItem(item.get());

        return new BoardDTO(boardRepository.save(board.get()));
    }

    private void updateBoardItem(Item item, Board board, ItemRequest itemRequest) {
        switch (itemRequest.getBoardAction()) {
            case ASSIGN_ITEM:
                item.setAssignedToId(itemRequest.getAssignedToId());
                break;
            case SET_ITEM_TYPE:
                item.setType(itemRequest.getType());
                break;
            case SET_ITEM_TITLE:
                item.setTitle(itemRequest.getTitle());
                break;
            case SET_ITEM_PARENT:
                Optional<Item> parentItem = board.getItemById(itemRequest.getParentId());
                Item parent = parentItem.isPresent() ? parentItem.get() : null;
                item.setParent(parent);
                break;
            case SET_ITEM_STATUS:
                item.setStatus(itemRequest.getStatus());
                break;
            case SET_ITEM_DUE_DATE:
                item.setDueDate(itemRequest.getDueDate());
                break;
            case SET_ITEM_IMPORTANCE:
                item.setImportance(itemRequest.getImportance());
                break;
            case SET_ITEM_DESCRIPTION:
                item.setDescription(itemRequest.getDescription());
                break;
            default:
                throw new IllegalArgumentException("Item operation is not supported!");
        }
    }

    public BoardDTO addComment(long boardId, long userId, long itemId, String content) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Optional<Item> item = board.get().getItemById(itemId);
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemId);
        }

        item.get().addComment(new Comment(userId, content));
        return new BoardDTO(boardRepository.save(board.get()));
    }

    public BoardDTO removeItem(long boardId, long itemId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Optional<Item> item = board.get().getItemById(itemId);
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemId);
        }

        board.get().removeItem(item.get());
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

        this.boardRepository.delete(board.get());
    }

    public List<BoardDTO> getBoardsByUserId(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User ID: "+ userId +" does not exist");
        }

        return userInBoardRepository.findByUserId(user).stream()
                .map(userInBoard -> new BoardDTO(userInBoard.getBoard())).collect(Collectors.toList());
    }
}
