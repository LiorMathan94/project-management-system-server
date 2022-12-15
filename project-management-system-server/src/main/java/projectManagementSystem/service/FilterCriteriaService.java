package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.criterias.*;
import projectManagementSystem.repository.BoardRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class FilterCriteriaService {

    @Autowired
    BoardRepository boardRepository;


    public List<Item> filterByProperty(Long boardId, Map<String, Object> map) {

        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null){
            throw new IllegalArgumentException("Could not find board ID: " + boardId);
        }
        List<Item> filteredItems = new ArrayList<>(board.getItems());
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            switch (entry.getKey()) {
                case "creator":
                    CreatorCriteria creatorCriteria = new CreatorCriteria((Integer) entry.getValue());
                    filteredItems = (creatorCriteria.meetCriteria(filteredItems));
                    break;
                case "assignTo":
                    AssignedToCriteria assignedToCriteria = new AssignedToCriteria((Integer) entry.getValue());
                    filteredItems = (assignedToCriteria.meetCriteria(filteredItems));
                    break;
                case "dueDate":
                    DueDateCriteria dueDateCriteria = new DueDateCriteria((LocalDate) entry.getValue());
                    filteredItems = (dueDateCriteria.meetCriteria(filteredItems));
                    break;
                case "parent":
                    ParentCriteria parentCriteria = new ParentCriteria((Item) entry.getValue());
                    filteredItems = (parentCriteria.meetCriteria(filteredItems));
                    break;
                case "status":
                    StatusCriteria statusCriteria = new StatusCriteria((String) entry.getValue());
                    filteredItems = (statusCriteria.meetCriteria(filteredItems));
                    break;
                case "type":
                    TypeCriteria typeCriteria = new TypeCriteria((String) entry.getValue());
                    filteredItems = (typeCriteria.meetCriteria(filteredItems));
                    break;
                case "importance":
                    ImportanceCriteria importanceCriteria = new ImportanceCriteria((Importance) entry.getValue());
                    filteredItems = (importanceCriteria.meetCriteria(filteredItems));
                    break;
            }
        }
        return filteredItems;
    }


}
