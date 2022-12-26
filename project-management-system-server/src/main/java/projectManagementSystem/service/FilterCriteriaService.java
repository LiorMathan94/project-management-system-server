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


    @Transactional
    public BoardDTO filterByProperty(Long boardId, FilterRequest filterRequest) {

        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }

        Field[] fields = filterRequest.getClass().getDeclaredFields();

        List<Item> filteredItems = new ArrayList<>(board.getItems());
        System.out.println(filteredItems);

            for (Field field : fields) {

                    switch (field.getName()) {
                        case "assignedToId":
                            if (filterRequest.getAssignedToId().size() > 0) {
                                AssignedToCriteria assignedToCriteria = new AssignedToCriteria(filterRequest.getAssignedToId());
                                filteredItems = (assignedToCriteria.meetCriteria(filteredItems));
                            }
                            break;
                        case "dueDate":
                        if (filterRequest.getDueDate().equals("") ){
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
                    System.out.println(field.getName());
                }


        System.out.println(filteredItems);
//        for (Map.Entry<CriteriaName, Object> entry : map.entrySet()) {
//
//            switch (entry.getKey()) {
//                case CREATOR:
//                    CreatorCriteria creatorCriteria = new CreatorCriteria((Integer) entry.getValue());
//                    filteredItems = (creatorCriteria.meetCriteria(filteredItems));
//                    break;
//                case ASSIGN_TO:
//                    AssignedToCriteria assignedToCriteria = new AssignedToCriteria((Integer) entry.getValue());
//                    filteredItems = (assignedToCriteria.meetCriteria(filteredItems));
//                    break;
//                case DUE_TO:
//                    DueDateCriteria dueDateCriteria = new DueDateCriteria((LocalDate) entry.getValue());
//                    filteredItems = (dueDateCriteria.meetCriteria(filteredItems));
//                    break;
//                case PARENT:
//                    ParentCriteria parentCriteria = new ParentCriteria((Item) entry.getValue());
//                    filteredItems = (parentCriteria.meetCriteria(filteredItems));
//                    break;
//                case STATUS:
//                    StatusCriteria statusCriteria = new StatusCriteria((String) entry.getValue());
//                    filteredItems = (statusCriteria.meetCriteria(filteredItems));
//                    break;
//                case TYPE:
//                    TypeCriteria typeCriteria = new TypeCriteria((String) entry.getValue());
//                    filteredItems = (typeCriteria.meetCriteria(filteredItems));
//                    break;
//                case IMPORTANCE:
//                    ImportanceCriteria importanceCriteria = new ImportanceCriteria((Importance) entry.getValue());
//                    filteredItems = (importanceCriteria.meetCriteria(filteredItems));
//                    break;
//            }
//        }
        board.setItems(filteredItems);
        BoardDTO updatedBoard = new BoardDTO(board);
        updatedBoard.setItems(board.getItemsByStatus());
        return updatedBoard;
    }

}
