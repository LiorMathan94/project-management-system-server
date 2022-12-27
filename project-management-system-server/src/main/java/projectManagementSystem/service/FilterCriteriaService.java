package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.FilterRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.criterias.*;
import projectManagementSystem.repository.BoardRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilterCriteriaService {
    @Autowired
    BoardRepository boardRepository;

    public BoardDTO filterByProperty(Long boardId, FilterRequest filterRequest) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Field[] fields = filterRequest.getClass().getDeclaredFields();
        List<Item> filteredItems = new ArrayList<>(board.getItems());

        for (Field field : fields) {
            switch (field.getName()) {
                case "assignedToId":
                    filterByAssignedUsers(filterRequest, filteredItems);
                    break;
                case "dueDate":
                    filterByDueDate(filterRequest, filteredItems);
                    break;
                case "status":
                    filterByStatus(filterRequest, filteredItems);
                    break;
                case "type":
                    filterByType(filterRequest, filteredItems);
                    break;
                case "importance":
                    filterByImportance(filterRequest, filteredItems);
                    break;
            }
        }

        board.setItems(filteredItems);
        return new BoardDTO(board);
    }

    private void filterByAssignedUsers(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getAssignedToId().size() > 0) {
            AssignedToCriteria assignedToCriteria = new AssignedToCriteria(filterRequest.getAssignedToId());
            filteredItems = (assignedToCriteria.meetCriteria(filteredItems));
        }
    }

    private void filterByDueDate(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getDueDate() != null) {
            DueDateCriteria dueDateCriteria = new DueDateCriteria(filterRequest.getDueDate());
            filteredItems = (dueDateCriteria.meetCriteria(filteredItems));
        }
    }

    private void filterByStatus(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getStatus().size() > 0) {
            StatusCriteria statusCriteria = new StatusCriteria(filterRequest.getStatus());
            filteredItems = (statusCriteria.meetCriteria(filteredItems));
        }
    }

    private void filterByType(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getType().size() > 0) {
            TypeCriteria typeCriteria = new TypeCriteria(filterRequest.getType());
            filteredItems = (typeCriteria.meetCriteria(filteredItems));
        }
    }

    private void filterByImportance(FilterRequest filterRequest, List<Item> filteredItems) {
        if (filterRequest.getImportance().size() > 0) {
            ImportanceCriteria importanceCriteria = new ImportanceCriteria(filterRequest.getImportance());
            filteredItems = (importanceCriteria.meetCriteria(filteredItems));
        }
    }
}
