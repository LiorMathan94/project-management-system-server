package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.BoardRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.criterias.*;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
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

    private void deleteStatusItems(Board board, String status) {
        for (Item item : board.getItemsByStatus().get(status)) {
            board.removeItem(item);
            this.itemRepository.delete(item);
        }
    }

    public List<Item> filterByProperty(List<Item> items, Map<String, Object> map) {

        List<Item> filteredItems = new ArrayList<>(items);
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            switch (entry.getKey()) {
                case "Creator":
                    CreatorCriteria creatorCriteria = new CreatorCriteria((Integer) entry.getValue());
                    filteredItems = (creatorCriteria.meetCriteria(filteredItems));
                    break;
                case "AssignTo":
                    AssignedToCriteria assignedToCriteria = new AssignedToCriteria((Integer) entry.getValue());
                    filteredItems = (assignedToCriteria.meetCriteria(filteredItems));
                    break;
                case "DueDate":
                    DueDateCriteria dueDateCriteria = new DueDateCriteria((LocalDate) entry.getValue());
                    filteredItems = (dueDateCriteria.meetCriteria(filteredItems));
                    break;
                case "Parent":
                    ParentCriteria parentCriteria = new ParentCriteria((Item) entry.getValue());
                    filteredItems = (parentCriteria.meetCriteria(filteredItems));
                    break;
                case "Status":
                    StatusCriteria statusCriteria = new StatusCriteria((String) entry.getValue());
                    filteredItems = (statusCriteria.meetCriteria(filteredItems));
                    break;
                case "Type":
                    TypeCriteria typeCriteria = new TypeCriteria((String) entry.getValue());
                    filteredItems = (typeCriteria.meetCriteria(filteredItems));
                    break;
                case "Importance":
                    ImportanceCriteria importanceCriteria = new ImportanceCriteria((Importance) entry.getValue());
                    filteredItems = (importanceCriteria.meetCriteria(filteredItems));
                    break;

            }
        }
        return filteredItems;
    }

}
