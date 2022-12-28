package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.FilterRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.criterias.*;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.UserRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilterCriteriaService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;

    public BoardDTO getFilteredBoard(Long boardId, FilterRequest filterRequest) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Field[] fields = filterRequest.getClass().getDeclaredFields();
        List<Item> filteredItems = new ArrayList<>(board.getItems());

        for (Field field : fields) {
            switch (field.getName()) {
                case "assignedToUser":
                    filteredItems = filterByAssignedUsers(filterRequest, filteredItems);
                    break;
                case "dueDate":
                    filteredItems = filterByDueDate(filterRequest, filteredItems);
                    break;
                case "status":
                    filteredItems = filterByStatus(filterRequest, filteredItems);
                    break;
                case "type":
                    filteredItems = filterByType(filterRequest, filteredItems);
                    break;
                case "importance":
                    filteredItems = filterByImportance(filterRequest, filteredItems);
                    break;
            }
        }

        board.setItems(filteredItems);
        return BoardDTO.createFromBoard(board);
    }

    private List<Item> filterByAssignedUsers(FilterRequest filterRequest, List<Item> filteredItems) {
        extractAssignedUsersId(filterRequest);

        if (filterRequest.getAssignedToUserId().size() > 0) {
            AssignedToCriteria assignedToCriteria = new AssignedToCriteria(filterRequest.getAssignedToUserId());
            filteredItems = (assignedToCriteria.meetCriteria(filteredItems));
        }

        return filteredItems;
    }

    private void extractAssignedUsersId(FilterRequest filterRequest) {
        for (String email : filterRequest.getAssignedToUser()) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                filterRequest.addToAssignedToUserId(user.get().getId());
            }
        }
    }

    private List<Item> filterByDueDate(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getDueDate() != null) {
            DueDateCriteria dueDateCriteria = new DueDateCriteria(filterRequest.getDueDate());
            filteredItems = (dueDateCriteria.meetCriteria(filteredItems));
        }

        return filteredItems;
    }

    private List<Item> filterByStatus(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getStatus().size() > 0) {
            StatusCriteria statusCriteria = new StatusCriteria(filterRequest.getStatus());
            filteredItems = (statusCriteria.meetCriteria(filteredItems));
        }

        return filteredItems;
    }

    private List<Item> filterByType(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getType().size() > 0) {
            TypeCriteria typeCriteria = new TypeCriteria(filterRequest.getType());
            filteredItems = (typeCriteria.meetCriteria(filteredItems));
        }

        return filteredItems;
    }

    private List<Item> filterByImportance(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getImportance().size() > 0) {
            ImportanceCriteria importanceCriteria = new ImportanceCriteria(filterRequest.getImportance());
            filteredItems = (importanceCriteria.meetCriteria(filteredItems));
        }

        return filteredItems;
    }
}
