package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                            if (filterRequest.getAssignedToId().size() > 0) {
                                AssignedToCriteria assignedToCriteria = new AssignedToCriteria(filterRequest.getAssignedToId());
                                filteredItems = (assignedToCriteria.meetCriteria(filteredItems));
                            }
                            break;
                        case "dueDate":
                        if (filterRequest.getDueDate() != null){
                            DueDateCriteria dueDateCriteria = new DueDateCriteria(filterRequest.getDueDate());
                            filteredItems = (dueDateCriteria.meetCriteria(filteredItems));
                        }
                        break;
                        case "status":
                            if (filterRequest.getStatus().size() > 0) {
                                StatusCriteria statusCriteria = new StatusCriteria(filterRequest.getStatus());
                                filteredItems = (statusCriteria.meetCriteria(filteredItems));
                            }
                            break;
                        case "type":
                            if (filterRequest.getType().size() > 0) {
                                TypeCriteria typeCriteria = new TypeCriteria(filterRequest.getType());
                                filteredItems = (typeCriteria.meetCriteria(filteredItems));
                            }
                            break;
                        case "importance":
                            if (filterRequest.getImportance().size() > 0) {
                                ImportanceCriteria importanceCriteria = new ImportanceCriteria(filterRequest.getImportance());
                                filteredItems = (importanceCriteria.meetCriteria(filteredItems));
                            }
                            break;


                    }
                }

        board.setItems(filteredItems);
        return new BoardDTO(board);
    }

}
