package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class AssignedToCriteria implements Criteria {
    private List<Long> assignedToId;

    public AssignedToCriteria(List<Long> assignedToId) {
        this.assignedToId = assignedToId;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (assignedToId.contains(item.getAssignedToId())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
