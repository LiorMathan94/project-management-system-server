package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

public class AssignedToCriteria implements Criteria {
    private List<Long> assignedToId;

    public AssignedToCriteria(List<Long> assignedToId) {
        this.assignedToId = assignedToId;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = items.stream().filter(item -> assignedToId.contains(item.getAssignedToId()))
                .collect(Collectors.toList());

        return filteredItems;
    }
}
