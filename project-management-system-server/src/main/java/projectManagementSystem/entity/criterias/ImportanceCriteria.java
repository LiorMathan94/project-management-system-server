package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ImportanceCriteria implements Criteria {
    private Importance importance;

    public ImportanceCriteria(Importance importance) {
        this.importance = importance;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (item.getImportance() == importance) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
