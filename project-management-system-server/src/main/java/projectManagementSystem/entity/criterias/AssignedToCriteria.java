package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AssignedToCriteria implements Criteria {
    private int assignedToId;

    public AssignedToCriteria(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (item.getAssignedToId() == assignedToId) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
