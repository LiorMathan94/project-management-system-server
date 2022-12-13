package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class CreatorCriteria implements Criteria {
    private int creatorId;

    public CreatorCriteria(int creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (item.getCreatorId() == creatorId) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
