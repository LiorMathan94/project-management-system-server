package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ParentCriteria implements Criteria {
    private List<Long> parent;

    public ParentCriteria(List<Long> parent) {
        this.parent = parent;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (parent.contains(item.getParent().getId())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
